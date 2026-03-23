package com.cognizant.greencity.dto.resourceusage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceUsageUpdateRequest {

    @Min(0)
    private Double quantity;

    @Size(max = 50)
    private String status;
}
