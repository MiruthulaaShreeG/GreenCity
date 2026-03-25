package com.cognizant.greencity.repository;

import com.cognizant.greencity.entity.CitizenReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitizenReportRepository extends JpaRepository<CitizenReport, Integer> {
    List<CitizenReport> findByCitizen_UserId(Integer userId);
}