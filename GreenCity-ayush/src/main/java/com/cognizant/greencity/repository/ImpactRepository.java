package com.cognizant.greencity.repository;

import com.cognizant.greencity.entity.Impact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImpactRepository extends JpaRepository<Impact, Integer> {
    List<Impact> findByProject_ProjectId(Integer projectId);

    Optional<Impact> findByImpactIdAndProject_ProjectId(Integer impactId, Integer projectId);
}

