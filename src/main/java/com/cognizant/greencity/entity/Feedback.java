package com.cognizant.greencity.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackID;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id")
    private User citizen;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String comments;
    private LocalDate date;
    private String status;

    public enum Category{
        Waste,Energy,Water;
    }

    public User getCitizen() {
        return citizen;
    }

    public void setCitizen(User citizen) {
        this.citizen = citizen;
    }

    public Integer getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(Integer feedbackID) {
        this.feedbackID = feedbackID;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    //Constructor//
    public Feedback(){

    }

    public Feedback(Integer feedbackID, User citizen, Category category, String comments, LocalDate date, String status) {
        this.feedbackID = feedbackID;
        this.citizen = citizen;
        this.category = category;
        this.comments = comments;
        this.date = date;
        this.status = status;
    }
}
