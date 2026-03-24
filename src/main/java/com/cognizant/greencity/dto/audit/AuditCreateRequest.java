package com.cognizant.greencity.dto.audit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditCreateRequest {
    @NotBlank
    @Size(max = 255)
    private String scope;


    private LocalDateTime date;

    @Size(max = 255)
    private String status;

    @Size(max = 2000)
    private String findings;

}

