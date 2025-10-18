package com.ddalkkak.service;

import com.ddalkkak.dto.CourseGenerationRequest;
import com.ddalkkak.dto.CourseGenerationResponse;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CourseGenerationService {

    private final Optional<CourseCacheService> cacheService;
    private final ClaudeApiService claudeApiService;
    private final LangfuseTraceService traceService;
    private final ObservationRegistry observationRegistry;

    public CourseGenerationService(
            @Autowired(required = false) CourseCacheService cacheService,
            ClaudeApiService claudeApiService,
            LangfuseTraceService traceService,
            @Autowired(required = false) ObservationRegistry observationRegistry) {
        this.cacheService = Optional.ofNullable(cacheService);
        this.claudeApiService = claudeApiService;
        this.traceService = traceService;
        this.observationRegistry = observationRegistry != null ? observationRegistry : ObservationRegistry.NOOP;
    }

    public CourseGenerationResponse generateCourses(CourseGenerationRequest request) {
        // Create OpenTelemetry observation for Langfuse tracing
        return Observation.createNotStarted("course.generation", observationRegistry)
                .lowCardinalityKeyValue("region", request.getRegion())
                .lowCardinalityKeyValue("dateType", request.getDateType())
                .highCardinalityKeyValue("budget", String.valueOf(request.getBudget()))
                .highCardinalityKeyValue("langfuse.operation.name", "course-generation")
                .observe(() -> {
                    // 1. Check cache (if Redis is enabled)
                    if (cacheService.isPresent()) {
                        CourseGenerationResponse cachedResponse = cacheService.get().getFromCache(request);
                        if (cachedResponse != null) {
                            log.info("Returning cached course for region: {}, dateType: {}",
                                    request.getRegion(), request.getDateType());
                            return cachedResponse;
                        }
                    } else {
                        log.debug("Redis cache is disabled, skipping cache check");
                    }

                    // 2. Start Langfuse trace
                    String traceId = traceService.startTrace(request);
                    long startTime = System.currentTimeMillis();

                    try {
                        // 3. Generate courses via Claude API (with Circuit Breaker and Fallback)
                        CourseGenerationResponse response = claudeApiService.generateCourses(request);

                        // 4. Save to cache (if Redis is enabled)
                        cacheService.ifPresent(service -> service.saveToCache(request, response));

                        // 5. Record trace
                        long duration = System.currentTimeMillis() - startTime;
                        traceService.recordGeneration(traceId, request, response, duration);

                        log.info("Successfully generated courses in {}ms for region: {}, dateType: {}",
                                duration, request.getRegion(), request.getDateType());

                        return response;

                    } catch (Exception e) {
                        traceService.recordError(traceId, e);
                        log.error("Failed to generate courses for region: {}, dateType: {}",
                                request.getRegion(), request.getDateType(), e);
                        throw e;
                    }
                });
    }
}
