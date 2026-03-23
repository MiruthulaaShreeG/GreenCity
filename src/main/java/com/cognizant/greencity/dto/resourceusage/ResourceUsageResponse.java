package com.cognizant.greencity.dto.resourceusage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceUsageResponse {

    private Integer usageId;
    private Integer resourceId;
    private Double quantity;
    private LocalDateTime date;
    private String status;
}
