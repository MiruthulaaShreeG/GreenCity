package com.cognizant.greencity.dao;

import com.cognizant.greencity.entity.FundUtilization;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.time.LocalDate;
import java.util.List;

public interface FundUtilizationRepository extends JpaRepository<FundUtilization, UUID> {
    List<FundUtilization> findByDate(LocalDate date);
}
