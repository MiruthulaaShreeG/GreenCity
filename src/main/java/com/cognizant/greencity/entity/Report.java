package com.cognizant.greencity.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reports")
@Getter
@Setter
@NoArgsConstructor
public class Report {

    public enum Scope {
        RESOURCE, PROJECT, FEEDBACK, COMPLIANCE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Integer reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audit_id")
    private Audit audit;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope", nullable = false)
    private Scope scope;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "metrics", nullable = false, columnDefinition = "TEXT")
    private String metrics;
    @Column(name = "generated_date", nullable = false)
    private LocalDateTime generatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generated_by", nullable = false)
    private User generatedBy;

}
