package com.ddalkkak.service;

import com.ddalkkak.dto.KakaoLocalSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Kakao Local API Service
 * 장소 검색 및 데이터 수집
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLocalService {

    @Qualifier("kakaoWebClient")
    private final WebClient kakaoWebClient;

    private static final int MAX_PAGE = 3; // 페이지당 15개 * 3 = 45개
    private static final int PAGE_SIZE = 15;

    /**
     * 키워드로 장소 검색 (카테고리 필터링)
     */
    public List<KakaoLocalSearchResponse.Document> searchPlacesByKeyword(
            String query,
            String categoryGroupCode,
            Double x,
            Double y,
            Integer radius
    ) {
        List<KakaoLocalSearchResponse.Document> allDocuments = new ArrayList<>();

        for (int page = 1; page <= MAX_PAGE; page++) {
            try {
                KakaoLocalSearchResponse response = searchPage(query, categoryGroupCode, x, y, radius, page);

                if (response != null && response.getDocuments() != null) {
                    allDocuments.addAll(response.getDocuments());

                    // 마지막 페이지 체크
                    if (response.getMeta() != null && Boolean.TRUE.equals(response.getMeta().getIsEnd())) {
                        break;
                    }
                } else {
                    break;
                }

                // Rate limiting 준수 (Kakao API: 10 req/sec)
                Thread.sleep(100);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Thread interrupted during sleep", e);
                break;
            } catch (Exception e) {
                log.error("Error searching places for query: {}, page: {}", query, page, e);
            }
        }

        log.info("Searched {} places for query: {}", allDocuments.size(), query);
        return allDocuments;
    }

    /**
     * 단일 페이지 검색 (재시도 로직 포함)
     */
    private KakaoLocalSearchResponse searchPage(
            String query,
            String categoryGroupCode,
            Double x,
            Double y,
            Integer radius,
            int page
    ) {
        return kakaoWebClient.get()
                .uri(uriBuilder -> {
                    var builder = uriBuilder
                            .path("/v2/local/search/keyword.json")
                            .queryParam("query", query)
                            .queryParam("page", page)
                            .queryParam("size", PAGE_SIZE);

                    if (categoryGroupCode != null) {
                        builder.queryParam("category_group_code", categoryGroupCode);
                    }
                    if (x != null && y != null) {
                        builder.queryParam("x", x);
                        builder.queryParam("y", y);
                    }
                    if (radius != null) {
                        builder.queryParam("radius", radius);
                    }

                    return builder.build();
                })
                .retrieve()
                .bodyToMono(KakaoLocalSearchResponse.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .maxBackoff(Duration.ofSeconds(5))
                        .filter(throwable -> !(throwable instanceof IllegalArgumentException)))
                .onErrorResume(e -> {
                    log.error("Failed to search Kakao API: query={}, page={}", query, page, e);
                    return Mono.empty();
                })
                .block();
    }

    /**
     * 지역 중심 좌표 반환
     */
    public static class RegionCoordinates {
        public static final double[] HONGDAE = {126.9244, 37.5563};
        public static final double[] GANGNAM = {127.0276, 37.4979};
        public static final double[] SEONGSU = {127.0557, 37.5443};
        public static final double[] YEONNAM = {126.9264, 37.5652};
        public static final double[] ITAEWON = {126.9942, 37.5347};

        public static double[] getCoordinates(String region) {
            return switch (region.toLowerCase()) {
                case "홍대" -> HONGDAE;
                case "강남" -> GANGNAM;
                case "성수" -> SEONGSU;
                case "연남" -> YEONNAM;
                case "이태원" -> ITAEWON;
                default -> throw new IllegalArgumentException("Unknown region: " + region);
            };
        }
    }
}
