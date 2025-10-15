package com.ddalkkak.controller;

import com.ddalkkak.dto.CourseGenerationRequest;
import com.ddalkkak.dto.CourseGenerationResponse;
import com.ddalkkak.service.CourseGenerationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseGenerationController.class)
class CourseGenerationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourseGenerationService courseGenerationService;

    @Test
    @DisplayName("유효한 요청으로 코스 생성 성공")
    void generateCourses_ValidRequest_Success() throws Exception {
        // Given
        CourseGenerationRequest request = CourseGenerationRequest.builder()
            .region("홍대")
            .dateType("문화데이트")
            .budget(100000)
            .build();

        CourseGenerationResponse mockResponse = createMockResponse();
        when(courseGenerationService.generateCourses(any())).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/courses/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.requestId").exists())
            .andExpect(jsonPath("$.generatedAt").exists())
            .andExpect(jsonPath("$.courses").isArray())
            .andExpect(jsonPath("$.courses.length()").value(3));
    }

    @Test
    @DisplayName("지역 누락 시 400 에러")
    void generateCourses_MissingRegion_BadRequest() throws Exception {
        // Given
        CourseGenerationRequest request = CourseGenerationRequest.builder()
            .dateType("문화데이트")
            .budget(100000)
            .build();

        // When & Then
        mockMvc.perform(post("/api/v1/courses/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("예산이 최소값 미만일 때 400 에러")
    void generateCourses_BudgetTooLow_BadRequest() throws Exception {
        // Given
        CourseGenerationRequest request = CourseGenerationRequest.builder()
            .region("홍대")
            .dateType("문화데이트")
            .budget(5000)  // 최소값 10,000원 미만
            .build();

        // When & Then
        mockMvc.perform(post("/api/v1/courses/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    private CourseGenerationResponse createMockResponse() {
        List<CourseGenerationResponse.PlaceDto> places = List.of(
            CourseGenerationResponse.PlaceDto.builder()
                .placeId("p1")
                .name("홍대 카페")
                .category("카페")
                .estimatedCost(15000)
                .estimatedDuration(60)
                .description("감성적인 카페")
                .build(),
            CourseGenerationResponse.PlaceDto.builder()
                .placeId("p2")
                .name("홍대 맛집")
                .category("식당")
                .estimatedCost(40000)
                .estimatedDuration(90)
                .description("맛있는 레스토랑")
                .build()
        );

        List<CourseGenerationResponse.CourseDto> courses = List.of(
            CourseGenerationResponse.CourseDto.builder()
                .courseId("c1")
                .title("홍대 감성 코스")
                .places(places)
                .totalCost(95000)
                .totalTime("4.5시간")
                .build(),
            CourseGenerationResponse.CourseDto.builder()
                .courseId("c2")
                .title("홍대 액티브 코스")
                .places(places)
                .totalCost(85000)
                .totalTime("5시간")
                .build(),
            CourseGenerationResponse.CourseDto.builder()
                .courseId("c3")
                .title("홍대 여유 코스")
                .places(places)
                .totalCost(75000)
                .totalTime("3.5시간")
                .build()
        );

        return CourseGenerationResponse.builder()
            .requestId(UUID.randomUUID().toString())
            .generatedAt(LocalDateTime.now())
            .courses(courses)
            .build();
    }
}
