package com.cognizant.greencity.entity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer userId;
    @Column(nullable=false)
    private String name;
    @Column(nullable=false)
    private String role;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(name="phone")
    private String phone;
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    @Column(name="status")
    private String status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AuditLog> auditLogs;

    public User(){

    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AuditLog> getAuditLogs() { return auditLogs; }
    public void setAuditLogs(List<AuditLog> auditLogs) { this.auditLogs = auditLogs; }

}


