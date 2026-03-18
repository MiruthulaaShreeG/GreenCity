package com.cognizant.greencity.repository;

import com.cognizant.greencity.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    List<Resource> findByProject_ProjectId(Integer projectId);
}

