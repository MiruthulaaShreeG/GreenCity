package com.example.sustainability.repository;

import com.example.sustainability.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    List<Report> findByScope(String scope);
    List<Report> findByProject_ProjectId(Integer projectId);
    List<Report> findByResource_ResourceId(Integer resourceId);
    List<Report> findByComplianceRecord_ComplianceId(Integer complianceId);
    List<Report> findByFeedback_FeedbackId(Integer feedbackId);
    List<Report> findByGeneratedBy_UserId(Integer userId);
}