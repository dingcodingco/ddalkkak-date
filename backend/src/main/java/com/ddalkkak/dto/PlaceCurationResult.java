package com.ddalkkak.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Claude API AI 큐레이션 결과 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceCurationResult {

    @JsonProperty("date_score")
    private Integer dateScore; // 1-10

    @JsonProperty("mood_tags")
    private String[] moodTags; // 최대 3개

    @JsonProperty("price_range")
    private String priceRange; // ₩, ₩₩, ₩₩₩

    @JsonProperty("best_time")
    private String bestTime; // 아침, 점심, 저녁, 야간

    @JsonProperty("recommendation")
    private String recommendation; // 50자 이내
}
