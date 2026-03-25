package com.cognizant.greencity.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.cognizant.greencity.entity.CitizenReport;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitizenReportCreateRequest {
    @NotNull
    private CitizenReport.ReportType type;

    @NotBlank
    @Size(max = 255)
    private String location;

    @NotBlank
    @Size(max = 255)
    private String status;
}
