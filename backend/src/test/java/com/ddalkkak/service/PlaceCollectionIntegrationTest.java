package com.ddalkkak.service;

import com.ddalkkak.domain.Place;
import com.ddalkkak.dto.KakaoLocalSearchResponse;
import com.ddalkkak.dto.PlaceCurationResult;
import com.ddalkkak.repository.PlaceRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Place Collection Integration Test
 * 실제 API 호출 통합 테스트 (10곳 샘플)
 *
 * @Disabled 제거하고 실행: ./gradlew test --tests PlaceCollectionIntegrationTest
 */
@SpringBootTest
@Disabled("Requires real API keys and should be run manually")
class PlaceCollectionIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(PlaceCollectionIntegrationTest.class);

    @Autowired
    private KakaoLocalService kakaoLocalService;

    @Autowired
    private PlaceCurationService placeCurationService;

    @Autowired
    private PlaceRepository placeRepository;

    @Test
    void testKakaoApiIntegration() {
        // Given
        String query = "홍대 카페";
        double[] coords = KakaoLocalService.RegionCoordinates.HONGDAE;

        // When
        List<KakaoLocalSearchResponse.Document> documents = kakaoLocalService.searchPlacesByKeyword(
                query,
                "CE7", // 카페
                coords[0],
                coords[1],
                2000
        );

        // Then
        assertThat(documents).isNotEmpty();
        assertThat(documents.size()).isLessThanOrEqualTo(45); // MAX_PAGE * PAGE_SIZE

        log.info("Found {} places for query: {}", documents.size(), query);

        // 첫 번째 장소 정보 출력
        if (!documents.isEmpty()) {
            KakaoLocalSearchResponse.Document first = documents.get(0);
            log.info("Sample Place: name={}, category={}, address={}",
                    first.getPlaceName(), first.getCategoryName(), first.getAddressName());
        }
    }

    @Test
    void testClaudeApiIntegration() {
        // Given
        Place samplePlace = Place.builder()
                .name("더 현대 서울 루프탑")
                .categoryName("음식점 > 카페")
                .region("여의도")
                .addressName("서울 영등포구 여의도동")
                .build();

        // When
        PlaceCurationResult curation = placeCurationService.curate(samplePlace);

        // Then
        assertThat(curation).isNotNull();
        assertThat(curation.getDateScore()).isBetween(1, 10);
        assertThat(curation.getMoodTags()).isNotEmpty();
        assertThat(curation.getMoodTags().length).isLessThanOrEqualTo(3);
        assertThat(curation.getPriceRange()).isIn("₩", "₩₩", "₩₩₩");
        assertThat(curation.getBestTime()).isIn("아침", "점심", "저녁", "야간");
        assertThat(curation.getRecommendation()).isNotBlank();
        assertThat(curation.getRecommendation().length()).isLessThanOrEqualTo(50);

        log.info("AI Curation Result:");
        log.info("  - Date Score: {}", curation.getDateScore());
        log.info("  - Mood Tags: {}", String.join(", ", curation.getMoodTags()));
        log.info("  - Price Range: {}", curation.getPriceRange());
        log.info("  - Best Time: {}", curation.getBestTime());
        log.info("  - Recommendation: {}", curation.getRecommendation());
    }

    @Test
    void testFullWorkflow_10Places() {
        // Given
        String region = "홍대";
        String query = region + " 카페";
        double[] coords = KakaoLocalService.RegionCoordinates.HONGDAE;
        int sampleSize = 10;

        // Phase 1: Kakao API로 장소 수집
        log.info("=== Phase 1: Collecting places from Kakao API ===");
        List<KakaoLocalSearchResponse.Document> documents = kakaoLocalService.searchPlacesByKeyword(
                query,
                "CE7",
                coords[0],
                coords[1],
                2000
        );

        assertThat(documents).isNotEmpty();

        // 샘플 10곳만 선택
        List<KakaoLocalSearchResponse.Document> samples = documents.stream()
                .limit(sampleSize)
                .toList();

        log.info("Collected {} sample places", samples.size());

        // Phase 2: DB 저장 및 AI 큐레이션
        log.info("=== Phase 2: Saving and curating places ===");
        int successCount = 0;

        for (KakaoLocalSearchResponse.Document doc : samples) {
            try {
                // DB 저장
                Place place = Place.builder()
                        .name(doc.getPlaceName())
                        .kakaoPlaceId(doc.getId())
                        .addressName(doc.getAddressName())
                        .roadAddressName(doc.getRoadAddressName())
                        .categoryName(doc.getCategoryName())
                        .categoryGroupCode(doc.getCategoryGroupCode())
                        .latitude(Double.parseDouble(doc.getY()))
                        .longitude(Double.parseDouble(doc.getX()))
                        .placeUrl(doc.getPlaceUrl())
                        .phone(doc.getPhone())
                        .region(region)
                        .build();

                Place saved = placeRepository.save(place);

                // AI 큐레이션
                PlaceCurationResult curation = placeCurationService.curate(saved);

                saved.updateCuration(
                        curation.getDateScore(),
                        curation.getMoodTags(),
                        curation.getPriceRange(),
                        curation.getBestTime(),
                        curation.getRecommendation()
                );

                placeRepository.save(saved);
                successCount++;

                log.info("[{}/{}] Processed: {} (score: {})",
                        successCount, sampleSize, saved.getName(), curation.getDateScore());

                // Rate limiting
                Thread.sleep(1000);

            } catch (Exception e) {
                log.error("Failed to process place: {}", doc.getPlaceName(), e);
            }
        }

        // Then
        assertThat(successCount).isGreaterThan(0);
        log.info("=== Test Complete: {}/{} places processed successfully ===", successCount, sampleSize);

        // 결과 검증
        List<Place> savedPlaces = placeRepository.findByRegion(region);
        assertThat(savedPlaces).hasSizeGreaterThanOrEqualTo(successCount);

        // 큐레이션 데이터 검증
        savedPlaces.forEach(place -> {
            if (place.getCuratedAt() != null) {
                assertThat(place.getDateScore()).isBetween(1, 10);
                assertThat(place.getMoodTags()).isNotNull();
                assertThat(place.getPriceRange()).isNotNull();
                assertThat(place.getBestTime()).isNotNull();
                assertThat(place.getRecommendation()).isNotBlank();
            }
        });
    }
}
