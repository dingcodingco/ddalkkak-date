package com.ddalkkak.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Kakao Local API 검색 응답 DTO
 */
@Getter
@Setter
public class KakaoLocalSearchResponse {

    private Meta meta;
    private List<Document> documents;

    @Getter
    @Setter
    public static class Meta {
        @JsonProperty("total_count")
        private Integer totalCount;

        @JsonProperty("pageable_count")
        private Integer pageableCount;

        @JsonProperty("is_end")
        private Boolean isEnd;
    }

    @Getter
    @Setter
    public static class Document {
        @JsonProperty("id")
        private String id;

        @JsonProperty("place_name")
        private String placeName;

        @JsonProperty("category_name")
        private String categoryName;

        @JsonProperty("category_group_code")
        private String categoryGroupCode;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("address_name")
        private String addressName;

        @JsonProperty("road_address_name")
        private String roadAddressName;

        @JsonProperty("x")
        private String x; // longitude

        @JsonProperty("y")
        private String y; // latitude

        @JsonProperty("place_url")
        private String placeUrl;

        @JsonProperty("distance")
        private String distance;
    }
}
