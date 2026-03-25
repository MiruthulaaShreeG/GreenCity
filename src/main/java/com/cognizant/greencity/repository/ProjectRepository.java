package com.cognizant.greencity.repository;

import com.cognizant.greencity.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByCreatedBy_UserId(Integer userId);
}
