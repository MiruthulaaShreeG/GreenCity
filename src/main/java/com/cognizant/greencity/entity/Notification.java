package com.cognizant.greencity.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Integer notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



    @Column(name = "entity_id")
    private Integer entityId;

    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "category")
    private String category;

    @Column(name = "status")
    private String status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;


    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }



}