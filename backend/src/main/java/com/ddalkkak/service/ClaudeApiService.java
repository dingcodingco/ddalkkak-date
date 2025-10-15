package com.ddalkkak.service;

import com.ddalkkak.config.ClaudeApiConfig;
import com.ddalkkak.dto.CourseGenerationRequest;
import com.ddalkkak.dto.CourseGenerationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClaudeApiService {

    private final ClaudeApiConfig config;
    private final ObjectMapper objectMapper;
    private final WebClient webClient = WebClient.builder()
        .baseUrl("https://api.anthropic.com")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();

    @CircuitBreaker(name = "claudeApi", fallbackMethod = "generateCoursesFallback")
    public CourseGenerationResponse generateCourses(CourseGenerationRequest request) {
        log.info("Calling Claude API for course generation: region={}, dateType={}, budget={}",
            request.getRegion(), request.getDateType(), request.getBudget());

        String prompt = buildPrompt(request);
        String response = callClaudeApi(prompt);

        return parseResponse(response, request);
    }

    private String buildPrompt(CourseGenerationRequest request) {
        return String.format("""
            당신은 서울 데이트 코스 추천 전문가입니다.
            아래 조건에 맞는 데이트 코스 3개를 추천해주세요.

            조건:
            - 지역: %s
            - 데이트 유형: %s
            - 예산: %,d원

            응답 형식 (JSON):
            {
              "courses": [
                {
                  "courseId": "c1",
                  "title": "코스 제목",
                  "places": [
                    {
                      "placeId": "p1",
                      "name": "장소명",
                      "category": "카테고리",
                      "estimatedCost": 15000,
                      "estimatedDuration": 60,
                      "description": "설명"
                    }
                  ],
                  "totalCost": 95000,
                  "totalTime": "4.5시간"
                }
              ]
            }

            주의사항:
            1. 각 코스는 3-5개의 장소로 구성
            2. 총 비용은 예산의 ±10%% 이내
            3. 이동 동선을 고려한 효율적인 경로
            4. 각 장소마다 상세한 설명 포함
            5. 반드시 JSON 형식으로만 응답
            """, request.getRegion(), request.getDateType(), request.getBudget());
    }

    private String callClaudeApi(String prompt) {
        Map<String, Object> requestBody = Map.of(
            "model", config.getModel(),
            "max_tokens", config.getMaxTokens(),
            "messages", List.of(
                Map.of(
                    "role", "user",
                    "content", prompt
                )
            )
        );

        try {
            return webClient.post()
                .uri("/v1/messages")
                .header("x-api-key", config.getApiKey())
                .header("anthropic-version", "2023-06-01")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofMillis(config.getTimeout()))
                .block();
        } catch (Exception e) {
            log.error("Claude API call failed", e);
            throw new RuntimeException("Claude API 호출 실패", e);
        }
    }

    private CourseGenerationResponse parseResponse(String apiResponse, CourseGenerationRequest request) {
        try {
            JsonNode root = objectMapper.readTree(apiResponse);
            JsonNode content = root.path("content").get(0);
            String text = content.path("text").asText();

            // Extract JSON from response
            String jsonText = extractJsonFromText(text);
            JsonNode coursesJson = objectMapper.readTree(jsonText);

            List<CourseGenerationResponse.CourseDto> courses = parseCourses(coursesJson);

            return CourseGenerationResponse.builder()
                .requestId(UUID.randomUUID().toString())
                .generatedAt(LocalDateTime.now())
                .courses(courses)
                .build();

        } catch (JsonProcessingException e) {
            log.error("Failed to parse Claude API response", e);
            throw new RuntimeException("Claude API 응답 파싱 실패", e);
        }
    }

    private String extractJsonFromText(String text) {
        // Extract JSON between ```json and ``` or just find { ... }
        if (text.contains("```json")) {
            int start = text.indexOf("```json") + 7;
            int end = text.indexOf("```", start);
            return text.substring(start, end).trim();
        } else if (text.contains("{")) {
            int start = text.indexOf("{");
            int end = text.lastIndexOf("}") + 1;
            return text.substring(start, end);
        }
        return text;
    }

    private List<CourseGenerationResponse.CourseDto> parseCourses(JsonNode coursesJson) throws JsonProcessingException {
        List<CourseGenerationResponse.CourseDto> courses = new ArrayList<>();
        JsonNode coursesArray = coursesJson.path("courses");

        for (JsonNode courseNode : coursesArray) {
            List<CourseGenerationResponse.PlaceDto> places = new ArrayList<>();
            JsonNode placesArray = courseNode.path("places");

            for (JsonNode placeNode : placesArray) {
                places.add(CourseGenerationResponse.PlaceDto.builder()
                    .placeId(placeNode.path("placeId").asText())
                    .name(placeNode.path("name").asText())
                    .category(placeNode.path("category").asText())
                    .estimatedCost(placeNode.path("estimatedCost").asInt())
                    .estimatedDuration(placeNode.path("estimatedDuration").asInt())
                    .description(placeNode.path("description").asText())
                    .build());
            }

            courses.add(CourseGenerationResponse.CourseDto.builder()
                .courseId(courseNode.path("courseId").asText())
                .title(courseNode.path("title").asText())
                .places(places)
                .totalCost(courseNode.path("totalCost").asInt())
                .totalTime(courseNode.path("totalTime").asText())
                .build());
        }

        return courses;
    }

    // Fallback method for Circuit Breaker
    public CourseGenerationResponse generateCoursesFallback(CourseGenerationRequest request, Exception e) {
        log.warn("Circuit breaker activated, using fallback for request: region={}, dateType={}",
            request.getRegion(), request.getDateType(), e);

        return CourseGenerationResponse.builder()
            .requestId(UUID.randomUUID().toString())
            .generatedAt(LocalDateTime.now())
            .courses(createFallbackCourses(request))
            .build();
    }

    private List<CourseGenerationResponse.CourseDto> createFallbackCourses(CourseGenerationRequest request) {
        // 룰 베이스 추천 로직 (간단한 버전)
        List<CourseGenerationResponse.PlaceDto> places = List.of(
            CourseGenerationResponse.PlaceDto.builder()
                .placeId("fallback-p1")
                .name(request.getRegion() + " 인기 카페")
                .category("카페")
                .estimatedCost((int) (request.getBudget() * 0.2))
                .estimatedDuration(60)
                .description("여유로운 시간을 보낼 수 있는 카페")
                .build(),
            CourseGenerationResponse.PlaceDto.builder()
                .placeId("fallback-p2")
                .name(request.getRegion() + " 맛집")
                .category("식당")
                .estimatedCost((int) (request.getBudget() * 0.4))
                .estimatedDuration(90)
                .description("맛있는 식사를 즐길 수 있는 곳")
                .build(),
            CourseGenerationResponse.PlaceDto.builder()
                .placeId("fallback-p3")
                .name(request.getRegion() + " 산책로")
                .category("야외")
                .estimatedCost(0)
                .estimatedDuration(60)
                .description("아름다운 경치를 즐기며 산책")
                .build()
        );

        return List.of(
            CourseGenerationResponse.CourseDto.builder()
                .courseId("fallback-c1")
                .title(request.getRegion() + " " + request.getDateType() + " 기본 코스")
                .places(places)
                .totalCost((int) (request.getBudget() * 0.9))
                .totalTime("3.5시간")
                .build()
        );
    }
}
