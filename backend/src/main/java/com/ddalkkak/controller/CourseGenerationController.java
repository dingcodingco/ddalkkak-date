package com.ddalkkak.controller;

import com.ddalkkak.dto.CourseGenerationRequest;
import com.ddalkkak.dto.CourseGenerationResponse;
import com.ddalkkak.service.CourseGenerationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Tag(name = "Course Generation", description = "데이트 코스 생성 API")
public class CourseGenerationController {

    private final CourseGenerationService courseGenerationService;

    @PostMapping("/generate")
    @Operation(summary = "데이트 코스 생성", description = "사용자 입력(지역, 유형, 예산)을 기반으로 AI가 데이트 코스 3개를 생성합니다")
    public ResponseEntity<CourseGenerationResponse> generateCourses(
        @Valid @RequestBody CourseGenerationRequest request
    ) {
        log.info("Received course generation request: region={}, dateType={}, budget={}",
            request.getRegion(), request.getDateType(), request.getBudget());

        CourseGenerationResponse response = courseGenerationService.generateCourses(request);

        log.info("Course generation completed: requestId={}, coursesCount={}",
            response.getRequestId(), response.getCourses().size());

        return ResponseEntity.ok(response);
    }
}
