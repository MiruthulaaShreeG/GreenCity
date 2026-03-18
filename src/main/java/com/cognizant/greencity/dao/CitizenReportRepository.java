package com.cognizant.greencity.dao;

import com.cognizant.greencity.entity.CitizenReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CitizenReportRepository extends JpaRepository<CitizenReport, Long> {
    @Query("SELECT r FROM CitizenReport r WHERE r.citizenID = :citizenId ORDER BY r.date DESC")
    List<CitizenReport> findByCitizenID(@Param("citizenId") Long citizenId);

    @Query("SELECT r FROM CitizenReport r WHERE r.citizenID = :citizenId AND r.status = :status")
    List<CitizenReport> findByCitizenIDAndStatus(@Param("citizenId") Long citizenId, @Param("status") String status);
    

    @Query("SELECT r FROM CitizenReport r WHERE r.type = :type ORDER BY r.date DESC")
    List<CitizenReport> findByType(@Param("type") CitizenReport.ReportType type);
    

    @Query("SELECT COUNT(r) > 0 FROM CitizenReport r WHERE r.reportID = :reportId")
    boolean existsById(@Param("reportId") Long reportId);
}

