package com.cognizant.greencity.repository;

import com.cognizant.greencity.entity.ResourceUsage;
import com.cognizant.greencity.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ResourceUsageRepository extends JpaRepository<ResourceUsage, UUID> {

    // Find usages by resource
    List<ResourceUsage> findByResource(Resource resource);

    // Find usages within a date range
    List<ResourceUsage> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find usages by status
    List<ResourceUsage> findByStatus(String status);
}
