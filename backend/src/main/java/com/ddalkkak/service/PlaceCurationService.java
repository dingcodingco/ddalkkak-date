package com.ddalkkak.service;

import com.ddalkkak.domain.Place;
import com.ddalkkak.dto.PlaceCurationResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * Place Curation Service using Claude API
 * AI 기반 장소 큐레이션 (데이트 적합성 분석)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceCurationService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${claude.api.key}")
    private String apiKey;

    @Value("${claude.api.model}")
    private String model;

    @Value("${claude.api.max-tokens}")
    private int maxTokens;

    private static final String CLAUDE_API_URL = "https://api.anthropic.com/v1/messages";
    private static final String ANTHROPIC_VERSION = "2023-06-01";

    /**
     * AI 큐레이터 Master Prompt
     */
    private static final String CURATION_PROMPT_TEMPLATE = """
            당신은 서울의 데이트 장소를 분석하는 전문 큐레이터입니다.

            다음 장소 정보를 분석하여 데이트 적합성을 평가해주세요:

            **장소 정보:**
            - 이름: %s
            - 카테고리: %s
            - 지역: %s
            - 주소: %s

            **분석 요청:**
            다음 5가지 항목을 JSON 형식으로 반환해주세요:

            1. date_score: 1-10점 (데이트 적합도 점수, 분위기/프라이버시/낭만도 종합 평가)
            2. mood_tags: 최대 3개의 분위기 해시태그 (예: ["로맨틱", "힙한", "조용한"])
            3. price_range: 1인당 예상 가격대 ("₩", "₩₩", "₩₩₩" 중 하나)
            4. best_time: 추천 시간대 ("아침", "점심", "저녁", "야간" 중 하나)
            5. recommendation: 데이트 추천 이유 (50자 이내, 한 문장)

            **응답 형식 (JSON만 반환):**
            {
              "date_score": 8,
              "mood_tags": ["로맨틱", "조용한", "감성적"],
              "price_range": "₩₩",
              "best_time": "저녁",
              "recommendation": "야경이 아름다운 루프탑 카페로 특별한 저녁 데이트에 완벽해요"
            }

            **중요:** JSON 형식만 반환하고, 다른 설명은 포함하지 마세요.
            """;

    /**
     * 장소 AI 큐레이션 수행
     */
    public PlaceCurationResult curate(Place place) {
        try {
            String prompt = String.format(
                    CURATION_PROMPT_TEMPLATE,
                    place.getName(),
                    place.getCategoryName(),
                    place.getRegion(),
                    place.getAddressName()
            );

            String claudeResponse = callClaudeApi(prompt);
            return parseClaudeResponse(claudeResponse);

        } catch (Exception e) {
            log.error("Failed to curate place: {}", place.getName(), e);
            return createDefaultCuration();
        }
    }

    /**
     * Claude API 호출
     */
    private String callClaudeApi(String prompt) {
        Map<String, Object> requestBody = Map.of(
                "model", model,
                "max_tokens", maxTokens,
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                )
        );

        return webClient.post()
                .uri(CLAUDE_API_URL)
                .header("x-api-key", apiKey)
                .header("anthropic-version", ANTHROPIC_VERSION)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(2, Duration.ofSeconds(2))
                        .maxBackoff(Duration.ofSeconds(10)))
                .onErrorResume(e -> {
                    log.error("Claude API call failed", e);
                    return Mono.empty();
                })
                .block();
    }

    /**
     * Claude API 응답 파싱
     */
    private PlaceCurationResult parseClaudeResponse(String response) throws JsonProcessingException {
        if (response == null || response.isEmpty()) {
            return createDefaultCuration();
        }

        // Claude API 응답 구조: { "content": [{ "text": "..." }] }
        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode contentArray = rootNode.get("content");

        if (contentArray == null || !contentArray.isArray() || contentArray.isEmpty()) {
            log.error("Invalid Claude API response structure");
            return createDefaultCuration();
        }

        String textContent = contentArray.get(0).get("text").asText();

        // JSON 블록 추출 (```json ... ``` 형식 처리)
        String jsonContent = extractJsonContent(textContent);

        // PlaceCurationResult로 파싱
        return objectMapper.readValue(jsonContent, PlaceCurationResult.class);
    }

    /**
     * JSON 콘텐츠 추출 (마크다운 코드 블록 제거)
     */
    private String extractJsonContent(String text) {
        // ```json ... ``` 제거
        String cleaned = text.trim();
        if (cleaned.startsWith("```json")) {
            cleaned = cleaned.substring(7);
        } else if (cleaned.startsWith("```")) {
            cleaned = cleaned.substring(3);
        }
        if (cleaned.endsWith("```")) {
            cleaned = cleaned.substring(0, cleaned.length() - 3);
        }
        return cleaned.trim();
    }

    /**
     * 기본 큐레이션 데이터 생성 (fallback)
     */
    private PlaceCurationResult createDefaultCuration() {
        return PlaceCurationResult.builder()
                .dateScore(5)
                .moodTags(new String[]{"일반적", "평범한"})
                .priceRange("₩₩")
                .bestTime("점심")
                .recommendation("데이트 장소로 고려해볼 만한 곳입니다")
                .build();
    }
}
