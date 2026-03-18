package com.cognizant.greencity.dto.project;

import java.time.LocalDate;

public class MilestoneResponse {
    private Integer milestoneId;
    private Integer projectId;
    private String title;
    private LocalDate date;
    private String status;

    public MilestoneResponse() {}

    public MilestoneResponse(Integer milestoneId, Integer projectId, String title, LocalDate date, String status) {
        this.milestoneId = milestoneId;
        this.projectId = projectId;
        this.title = title;
        this.date = date;
        this.status = status;
    }

    public Integer getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(Integer milestoneId) {
        this.milestoneId = milestoneId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

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

