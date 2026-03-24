package com.cognizant.greencity.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCreateRequest {

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255)
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 100000)
    private String description;

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;

    @NotNull(message = "Budget cannot be null")
    @PositiveOrZero(message = "Budget cannot be negative")
    private BigDecimal budget;

    @NotBlank(message = "Status cannot be blank")
    @Size(max = 255)
    private String status;

    /**
     * Custom validation to ensure the end date is strictly after the start date.
     * The @AssertTrue annotation tells the validator to ensure this method returns true.
     */
    @AssertTrue(message = "End date must be strictly after the start date")
    private boolean isEndDateValid() {
        // We return true if either is null to let the @NotNull annotations do their job
        // and avoid a NullPointerException here.
        if (startDate == null || endDate == null) {
            return true;
        }
        return endDate.isAfter(startDate);
    }
}