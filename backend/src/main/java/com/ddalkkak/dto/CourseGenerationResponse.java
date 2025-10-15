package com.ddalkkak.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "코스 생성 응답")
public class CourseGenerationResponse {

    @Schema(description = "요청 ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String requestId;

    @Schema(description = "생성 시각", example = "2025-10-13T14:30:00")
    private LocalDateTime generatedAt;

    @Schema(description = "생성된 코스 목록 (3개)")
    private List<CourseDto> courses;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "코스 정보")
    public static class CourseDto {

        @Schema(description = "코스 ID", example = "c1")
        private String courseId;

        @Schema(description = "코스 제목", example = "홍대 감성 문화 코스")
        private String title;

        @Schema(description = "코스에 포함된 장소 목록")
        private List<PlaceDto> places;

        @Schema(description = "총 예상 비용 (원)", example = "95000")
        private Integer totalCost;

        @Schema(description = "총 소요 시간", example = "4.5시간")
        private String totalTime;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "장소 정보")
    public static class PlaceDto {

        @Schema(description = "장소 ID", example = "p1")
        private String placeId;

        @Schema(description = "장소명", example = "홍대 앞 카페거리")
        private String name;

        @Schema(description = "카테고리", example = "카페")
        private String category;

        @Schema(description = "예상 비용 (원)", example = "15000")
        private Integer estimatedCost;

        @Schema(description = "예상 소요 시간 (분)", example = "60")
        private Integer estimatedDuration;

        @Schema(description = "설명", example = "감성적인 분위기의 카페에서 여유로운 시간")
        private String description;
    }
}
