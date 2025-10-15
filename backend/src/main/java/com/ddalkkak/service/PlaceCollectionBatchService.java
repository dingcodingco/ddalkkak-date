package com.ddalkkak.service;

import com.ddalkkak.domain.Place;
import com.ddalkkak.dto.KakaoLocalSearchResponse;
import com.ddalkkak.dto.PlaceCurationResult;
import com.ddalkkak.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Place Collection Batch Service
 * Kakao API 장소 수집 + Claude API 큐레이션 통합 배치
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceCollectionBatchService {

    private final KakaoLocalService kakaoLocalService;
    private final PlaceCurationService placeCurationService;
    private final PlaceRepository placeRepository;

    // Tier 1 지역 목록
    private static final List<String> TIER1_REGIONS = List.of(
            "홍대", "강남", "성수", "연남", "이태원"
    );

    // 카테고리별 검색 키워드
    private static final List<String> SEARCH_KEYWORDS = List.of(
            "카페", "레스토랑", "음식점", "바", "디저트"
    );

    private static final int TARGET_PLACES_PER_REGION = 100;

    /**
     * Phase 1 + Phase 2 통합 배치 실행
     * - Phase 1: Kakao API로 500곳 수집
     * - Phase 2: Claude API로 AI 큐레이션
     */
    @Transactional
    public void collectAndCuratePlaces() {
        log.info("=== Starting Place Collection & Curation Batch ===");

        int totalCollected = 0;
        int totalCurated = 0;

        for (String region : TIER1_REGIONS) {
            try {
                log.info("Processing region: {}", region);

                // Phase 1: 장소 수집
                List<Place> collectedPlaces = collectPlacesForRegion(region);
                totalCollected += collectedPlaces.size();

                log.info("Collected {} places for region: {}", collectedPlaces.size(), region);

                // Phase 2: AI 큐레이션
                int curatedCount = curatePlaces(collectedPlaces);
                totalCurated += curatedCount;

                log.info("Curated {} places for region: {}", curatedCount, region);

            } catch (Exception e) {
                log.error("Error processing region: {}", region, e);
            }
        }

        log.info("=== Batch Complete ===");
        log.info("Total Collected: {}", totalCollected);
        log.info("Total Curated: {}", totalCurated);
    }

    /**
     * Phase 1: 특정 지역의 장소 수집
     */
    private List<Place> collectPlacesForRegion(String region) {
        List<Place> places = new ArrayList<>();
        double[] coords = KakaoLocalService.RegionCoordinates.getCoordinates(region);

        for (String keyword : SEARCH_KEYWORDS) {
            if (places.size() >= TARGET_PLACES_PER_REGION) {
                break;
            }

            String query = region + " " + keyword;
            List<KakaoLocalSearchResponse.Document> documents = kakaoLocalService.searchPlacesByKeyword(
                    query,
                    null, // 카테고리 그룹 코드 (null = 전체)
                    coords[0], // x (longitude)
                    coords[1], // y (latitude)
                    2000 // 반경 2km
            );

            for (KakaoLocalSearchResponse.Document doc : documents) {
                if (places.size() >= TARGET_PLACES_PER_REGION) {
                    break;
                }

                // 중복 체크
                if (placeRepository.findByKakaoPlaceId(doc.getId()).isPresent()) {
                    continue;
                }

                Place place = convertToPlace(doc, region);
                Place saved = placeRepository.save(place);
                places.add(saved);
            }

            // Rate limiting (Kakao API: 10 req/sec)
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Thread interrupted", e);
            }
        }

        return places;
    }

    /**
     * Phase 2: 수집된 장소들 AI 큐레이션
     */
    private int curatePlaces(List<Place> places) {
        int curatedCount = 0;

        for (Place place : places) {
            try {
                // AI 큐레이션 수행
                PlaceCurationResult curation = placeCurationService.curate(place);

                // 큐레이션 데이터 업데이트
                place.updateCuration(
                        curation.getDateScore(),
                        curation.getMoodTags(),
                        curation.getPriceRange(),
                        curation.getBestTime(),
                        curation.getRecommendation()
                );

                placeRepository.save(place);
                curatedCount++;

                log.debug("Curated place: {} (score: {})", place.getName(), curation.getDateScore());

                // Rate limiting (Claude API 호출 간격)
                Thread.sleep(1000); // 1초 대기

            } catch (Exception e) {
                log.error("Failed to curate place: {}", place.getName(), e);
            }
        }

        return curatedCount;
    }

    /**
     * Kakao Document → Place Entity 변환
     */
    private Place convertToPlace(KakaoLocalSearchResponse.Document doc, String region) {
        return Place.builder()
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
    }

    /**
     * 기존 장소들 재큐레이션 (AI 분석만 재실행)
     */
    @Transactional
    public void recuratePlaces() {
        log.info("=== Starting Place Re-Curation ===");

        List<Place> uncuratedPlaces = placeRepository.findUncuratedPlaces();
        log.info("Found {} uncurated places", uncuratedPlaces.size());

        int curatedCount = curatePlaces(uncuratedPlaces);

        log.info("=== Re-Curation Complete ===");
        log.info("Re-Curated: {}/{}", curatedCount, uncuratedPlaces.size());
    }
}
