package com.cognizant.greencity.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImpactUpdateRequest {

    private LocalDateTime date;

    @Size(max = 1000000)
    private String metricsJson;

    @Size(max = 255)
    private String status;
}
