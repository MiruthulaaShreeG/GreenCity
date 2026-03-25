package com.cognizant.greencity.repository;

import com.cognizant.greencity.entity.ResourceUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceUsageRepository extends JpaRepository<ResourceUsage, Integer> {
    List<ResourceUsage> findByResource_ResourceId(Integer resourceId);
}

