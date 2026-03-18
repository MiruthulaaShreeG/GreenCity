package com.cognizant.greencity.dao;

import com.cognizant.greencity.entity.Audit;
import com.cognizant.greencity.entity.ComplianceRecord;
import com.cognizant.greencity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {
    
    // Find audits by status
    List<Audit> findByStatus(String status);

    // Find audits by scope
    List<Audit> findByScope(String scope);

    // Find audits by officer
    List<Audit> findByOfficer(User officer);

    // Find audits by compliance record
    List<Audit> findByComplianceRecord(ComplianceRecord complianceRecord);

    // Find audits created after a specific date
    List<Audit> findByDateAfter(LocalDateTime date);

    // Find audits created before a specific date
    List<Audit> findByDateBefore(LocalDateTime date);

    // Find audits within a date range
    List<Audit> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find open audits assigned to an officer
    @Query("SELECT a FROM Audit a WHERE a.officer = :officer AND (a.status = 'Open' OR a.status = 'In Review') ORDER BY a.date DESC")
    List<Audit> findOpenAuditsByOfficer(@Param("officer") User officer);


}
