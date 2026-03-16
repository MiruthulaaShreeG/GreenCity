package com.cognizant.greencity.dao;

import com.cognizant.greencity.entity.CitizenReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenReportRepository extends JpaRepository<CitizenReport,Long> {

}

