package com.ddalkkak.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "코스 생성 요청")
public class CourseGenerationRequest {

    @NotBlank(message = "지역은 필수입니다")
    @Schema(description = "데이트 지역", example = "홍대", required = true)
    private String region;

    @NotBlank(message = "데이트 유형은 필수입니다")
    @Schema(description = "데이트 유형", example = "문화데이트", required = true)
    private String dateType;

    @NotNull(message = "예산은 필수입니다")
    @Min(value = 10000, message = "예산은 최소 10,000원 이상이어야 합니다")
    @Schema(description = "예산 (원)", example = "100000", required = true)
    private Integer budget;
}
