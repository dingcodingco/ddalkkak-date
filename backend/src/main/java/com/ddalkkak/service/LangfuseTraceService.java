package com.ddalkkak.service;

import com.ddalkkak.config.LangfuseConfig;
import com.ddalkkak.dto.CourseGenerationRequest;
import com.ddalkkak.dto.CourseGenerationResponse;
import com.langfuse.client.LangfuseClient;
import io.opentelemetry.api.trace.Span;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LangfuseTraceService {

    private final LangfuseConfig config;
    private LangfuseClient langfuseClient;

    @PostConstruct
    public void init() {
        if (config.isEnabled()) {
            try {
                langfuseClient = LangfuseClient.builder()
                    .url(config.getBaseUrl())
                    .credentials(config.getPublicKey(), config.getSecretKey())
                    .build();
                log.info("Langfuse SDK initialized successfully: baseUrl={}", config.getBaseUrl());
            } catch (Exception e) {
                log.error("Failed to initialize Langfuse SDK", e);
                langfuseClient = null;
            }
        } else {
            log.info("Langfuse is disabled, skipping initialization");
        }
    }

    public String startTrace(CourseGenerationRequest request) {
        if (!config.isEnabled() || langfuseClient == null) {
            log.debug("Langfuse is disabled, skipping trace");
            return null;
        }

        try {
            String traceId = UUID.randomUUID().toString();

            // Langfuse Java SDK 0.1.0은 주로 Prompt Management를 위한 것이므로
            // Tracing은 로깅으로 처리하고 메타데이터만 저장
            Map<String, Object> traceData = new HashMap<>();
            traceData.put("traceId", traceId);
            traceData.put("service", "course-generation");
            traceData.put("region", request.getRegion());
            traceData.put("dateType", request.getDateType());
            traceData.put("budget", request.getBudget());

            log.info("[Langfuse] Started trace: traceId={}, region={}, dateType={}, budget={}",
                traceId, request.getRegion(), request.getDateType(), request.getBudget());

            return traceId;
        } catch (Exception e) {
            log.error("[Langfuse] Failed to start trace", e);
            return null;
        }
    }

    public void recordGeneration(String traceId, CourseGenerationRequest request,
                                  CourseGenerationResponse response, long durationMs) {
        if (!config.isEnabled() || langfuseClient == null || traceId == null) {
            return;
        }

        try {
            // Token usage calculation (approximate)
            int estimatedPromptTokens = calculatePromptTokens(request);
            int estimatedCompletionTokens = calculateCompletionTokens(response);
            double estimatedCost = calculateCost(estimatedPromptTokens, estimatedCompletionTokens);

            // Add LLM metadata to current OpenTelemetry span
            Span currentSpan = Span.current();
            if (currentSpan != null) {
                currentSpan.setAttribute("gen_ai.usage.prompt_tokens", estimatedPromptTokens);
                currentSpan.setAttribute("gen_ai.usage.completion_tokens", estimatedCompletionTokens);
                currentSpan.setAttribute("gen_ai.usage.total_tokens", estimatedPromptTokens + estimatedCompletionTokens);
                currentSpan.setAttribute("gen_ai.usage.cost", estimatedCost);
                currentSpan.setAttribute("gen_ai.response.courses_count", response.getCourses().size());
            }

            log.info("[Langfuse] Recorded generation: traceId={}, model={}, duration={}ms, " +
                    "coursesCount={}, estimatedTokens={}/{} (prompt/completion), estimatedCost=${}",
                traceId, "claude-sonnet-4-20250514", durationMs,
                response.getCourses().size(),
                estimatedPromptTokens, estimatedCompletionTokens, estimatedCost);

        } catch (Exception e) {
            log.error("[Langfuse] Failed to record generation", e);
        }
    }

    public void recordError(String traceId, Exception error) {
        if (!config.isEnabled() || langfuseClient == null || traceId == null) {
            return;
        }

        try {
            log.error("[Langfuse] Recorded error trace: traceId={}, error={}, message={}",
                traceId, error.getClass().getSimpleName(), error.getMessage());
        } catch (Exception e) {
            log.error("[Langfuse] Failed to record error", e);
        }
    }

    // Token usage estimation (approximate)
    private int calculatePromptTokens(CourseGenerationRequest request) {
        // Rough estimate: prompt template (~500 tokens) + request data (~100 tokens)
        return 600;
    }

    private int calculateCompletionTokens(CourseGenerationResponse response) {
        // Rough estimate: 150 tokens per course * number of courses
        return response.getCourses().size() * 150;
    }

    // Cost calculation for Claude Sonnet 4
    // Input: $3 per MTok, Output: $15 per MTok
    private double calculateCost(int promptTokens, int completionTokens) {
        double inputCost = (promptTokens / 1_000_000.0) * 3.0;
        double outputCost = (completionTokens / 1_000_000.0) * 15.0;
        return inputCost + outputCost;
    }
}
