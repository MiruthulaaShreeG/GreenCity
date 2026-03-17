package com.cognizant.greencity.dto;

import lombok.Data;
import java.time.LocalDateTime;
@Data
public class CitizenReportDTO {
    private long reportId;
    private int citizenId;
    private String type;
    private String location;
    private String status;
    private LocalDateTime date;
}