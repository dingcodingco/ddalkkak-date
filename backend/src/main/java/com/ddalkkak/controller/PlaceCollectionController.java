package com.ddalkkak.controller;

import com.ddalkkak.service.PlaceCollectionBatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Place Collection Controller
 * 장소 수집 및 큐레이션 배치 작업 실행
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/places/collection")
@RequiredArgsConstructor
@Tag(name = "Place Collection", description = "장소 데이터 수집 및 AI 큐레이션 API")
public class PlaceCollectionController {

    private final PlaceCollectionBatchService placeCollectionBatchService;

    /**
     * 장소 수집 + AI 큐레이션 배치 실행
     */
    @PostMapping("/batch")
    @Operation(summary = "장소 수집 및 AI 큐레이션 배치 실행",
            description = "Kakao API로 500곳 수집 + Claude API로 AI 큐레이션 (Phase 1 + Phase 2)")
    public ResponseEntity<Map<String, String>> runCollectionBatch() {
        log.info("Starting place collection batch...");

        try {
            // @Async 비동기 실행
            placeCollectionBatchService.collectAndCuratePlaces();

            return ResponseEntity.ok(Map.of(
                    "status", "started",
                    "message", "Place collection and curation batch started successfully"
            ));
        } catch (Exception e) {
            log.error("Failed to start batch", e);
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Failed to start batch: " + e.getMessage()
            ));
        }
    }

    /**
     * 기존 장소 재큐레이션 (AI 분석만 재실행)
     */
    @PostMapping("/recurate")
    @Operation(summary = "기존 장소 재큐레이션",
            description = "수집된 장소들에 대해 AI 큐레이션만 재실행 (Claude API)")
    public ResponseEntity<Map<String, String>> recuratePlaces() {
        log.info("Starting place re-curation...");

        try {
            // @Async 비동기 실행
            placeCollectionBatchService.recuratePlaces();

            return ResponseEntity.ok(Map.of(
                    "status", "started",
                    "message", "Place re-curation started successfully"
            ));
        } catch (Exception e) {
            log.error("Failed to start re-curation", e);
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Failed to start re-curation: " + e.getMessage()
            ));
        }
    }
}
