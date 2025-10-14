package com.ddalkkak.controller;

import com.ddalkkak.dto.HealthCheckResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "Health Check", description = "서버 상태 확인 API")
@RestController
@RequestMapping("/api/v1")
public class HealthCheckController {

    @Operation(summary = "Health Check", description = "서버 상태를 확인합니다.")
    @GetMapping("/health")
    public ResponseEntity<HealthCheckResponse> healthCheck() {
        HealthCheckResponse response = HealthCheckResponse.builder()
                .status("UP")
                .message("딸깍데이트 백엔드 서버가 정상적으로 실행 중입니다.")
                .timestamp(LocalDateTime.now())
                .version("0.0.1-SNAPSHOT")
                .build();

        return ResponseEntity.ok(response);
    }
}
