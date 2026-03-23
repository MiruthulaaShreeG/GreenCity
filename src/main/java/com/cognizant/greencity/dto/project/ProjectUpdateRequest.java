package com.cognizant.greencity.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUpdateRequest {

    @Size(max = 255)
    private String title;

    @Size(max = 100000)
    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal budget;

    @Size(max = 255)
    private String status;
}
