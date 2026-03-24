package com.cognizant.greencity.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedbackid")
    private Integer feedbackId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id")
    private User citizen;
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;
    @Column(name = "comments")
    private String comments;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "status")
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
        return feedbackId;
    }

    public void setFeedbackID(Integer feedbackID) {
        this.feedbackId = feedbackID;
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
        this.feedbackId = feedbackID;
        this.citizen = citizen;
        this.category = category;
        this.comments = comments;
        this.date = date;
        this.status = status;
    }
}
