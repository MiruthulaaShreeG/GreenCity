package com.cognizant.greencity.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "compliance_records")
public class ComplianceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compliance_id")
    private Integer complianceId;

    @Column(name = "entity_id", nullable = false)
    private Integer entityId;

    @Column(name = "entity_type", nullable = false)
    private String entityType; // "Project" or "Resource"

    @Column(name = "result")
    private String result; // e.g., "Passed", "Failed", "Warning"

    @Column(name = "date")
    private LocalDateTime date;


    @Column(name = "notes")
    private String notes;

    /**
     * One ComplianceRecord can be referenced by many Audit rows.
     * Ref: audits.compliance_id -> compliance_records.compliance_id
     */
    @OneToMany(mappedBy = "complianceRecord")
    private List<Audit> audits;

    public ComplianceRecord() { }

    public Integer getComplianceId() {
        return complianceId;
    }

    public void setComplianceId(Integer complianceId) {
        this.complianceId = complianceId;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

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

    public List<Audit> getAudits() {
        return audits;
    }

    public void setAudits(List<Audit> audits) {
        this.audits = audits;
    }
}