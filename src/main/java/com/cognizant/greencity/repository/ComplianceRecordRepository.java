package com.cognizant.greencity.repository;

import com.cognizant.greencity.entity.ComplianceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplianceRecordRepository extends JpaRepository<ComplianceRecord, Integer> {
}

