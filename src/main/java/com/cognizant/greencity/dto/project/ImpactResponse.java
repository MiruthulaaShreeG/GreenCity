package com.cognizant.greencity.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImpactResponse {
    private Integer impactId;
    private Integer projectId;
    private LocalDateTime date;
    private String metricsJson;
    private String status;
}
