package com.cognizant.greencity.dao;

import com.cognizant.greencity.entity.ComplianceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ComplianceRecordRepository extends JpaRepository<ComplianceRecord, Integer> {

        Optional<ComplianceRecord> findById(Integer id);
        
    // Find compliance records created after a specific date
    List<ComplianceRecord> findByDateAfter(LocalDateTime date);

    // Find compliance records created before a specific date
    List<ComplianceRecord> findByDateBefore(LocalDateTime date);

    // Find compliance records within a date range
    List<ComplianceRecord> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Custom query to find compliance record by entity type and entity id
    @Query("SELECT c FROM ComplianceRecord c WHERE c.entityId = :entityId AND c.entityType = :entityType")
    Optional<ComplianceRecord> findByEntityIdAndEntityType(
            @Param("entityId") Integer entityId,
            @Param("entityType") String entityType);
}
