package com.cognizant.greencity.dto.project;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class MilestoneUpdateRequest {

    @Size(max = 255)
    private String title;

    private LocalDate date;

    @Size(max = 255)
    private String status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

