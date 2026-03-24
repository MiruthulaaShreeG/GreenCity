package com.cognizant.greencity.dto.project;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImpactCreateRequest {


    private LocalDateTime date;

    @NotBlank
    @Size(max = 1000000)
    private String metricsJson;

    @NotBlank
    @Size(max = 255)
    private String status;
}
