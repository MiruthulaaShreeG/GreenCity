package com.cognizant.greencity.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.cognizant.greencity.entity.CitizenReport;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitizenReportUpdateRequest {
    private CitizenReport.ReportType type;

    @Size(max = 255)
    private String location;

    @Size(max = 255)
    private String status;
}
