package com.cognizant.greencity.repository;

import com.cognizant.greencity.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MilestoneRepository extends JpaRepository<Milestone, Integer> {
    List<Milestone> findByProject_ProjectId(Integer projectId);

    Optional<Milestone> findByMilestoneIdAndProject_ProjectId(Integer milestoneId, Integer projectId);
}

