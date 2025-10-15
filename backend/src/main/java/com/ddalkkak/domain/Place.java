package com.ddalkkak.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Place Entity
 * 카카오 로컬 API로 수집한 장소 정보 + AI 큐레이션 데이터
 */
@Entity
@Table(name = "places")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Kakao API Basic Info
    @Column(nullable = false)
    private String name;

    @Column(name = "kakao_place_id", unique = true, nullable = false)
    private String kakaoPlaceId;

    @Column(name = "address_name")
    private String addressName;

    @Column(name = "road_address_name")
    private String roadAddressName;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_group_code")
    private String categoryGroupCode;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "place_url")
    private String placeUrl;

    @Column(name = "phone")
    private String phone;

    // Region Classification
    @Column(nullable = false)
    private String region; // 홍대, 강남, 성수, 연남, 이태원

    // AI Curation Fields (Claude API)
    @Column(name = "date_score")
    private Integer dateScore; // 1-10

    @Column(name = "mood_tags", columnDefinition = "text[]")
    private String[] moodTags; // 최대 3개

    @Column(name = "price_range", length = 10)
    private String priceRange; // ₩, ₩₩, ₩₩₩

    @Column(name = "best_time", length = 20)
    private String bestTime; // 아침, 점심, 저녁, 야간

    @Column(name = "recommendation", columnDefinition = "TEXT")
    private String recommendation; // 50자 이내

    @Column(name = "curated_at")
    private LocalDateTime curatedAt;

    // Metadata
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * AI 큐레이션 데이터 업데이트
     */
    public void updateCuration(Integer dateScore, String[] moodTags, String priceRange,
                               String bestTime, String recommendation) {
        this.dateScore = dateScore;
        this.moodTags = moodTags;
        this.priceRange = priceRange;
        this.bestTime = bestTime;
        this.recommendation = recommendation;
        this.curatedAt = LocalDateTime.now();
    }
}
