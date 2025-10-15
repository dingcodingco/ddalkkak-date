package com.ddalkkak.service;

import com.ddalkkak.dto.CourseGenerationRequest;
import com.ddalkkak.dto.CourseGenerationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseGenerationService {

    private final CourseCacheService cacheService;
    private final ClaudeApiService claudeApiService;
    private final LangfuseTraceService traceService;

    public CourseGenerationResponse generateCourses(CourseGenerationRequest request) {
        // 1. Check cache
        CourseGenerationResponse cachedResponse = cacheService.getFromCache(request);
        if (cachedResponse != null) {
            log.info("Returning cached course for region: {}, dateType: {}",
                request.getRegion(), request.getDateType());
            return cachedResponse;
        }

        // 2. Start Langfuse trace
        String traceId = traceService.startTrace(request);
        long startTime = System.currentTimeMillis();

        try {
            // 3. Generate courses via Claude API (with Circuit Breaker and Fallback)
            CourseGenerationResponse response = claudeApiService.generateCourses(request);

            // 4. Save to cache
            cacheService.saveToCache(request, response);

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
    }
}
