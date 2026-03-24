package com.cognizant.greencity.dto.compliance;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class ComplianceRecordUpdateRequest {

    @Size(max = 255)
    private String result;

    private LocalDateTime date;

    @Size(max = 255)
    private String notes;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

