package com.cognizant.greencity.repository;

import com.cognizant.greencity.entity.ResourceUsage;
import com.cognizant.greencity.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ResourceUsageRepository extends JpaRepository<ResourceUsage, Integer> {

    // Find usages by resource
    List<ResourceUsage> findByResource_ResourceId(Integer resourceId);
}
