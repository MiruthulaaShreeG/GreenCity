package com.cognizant.greencity.repository;

import com.cognizant.greencity.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {

    // Custom finder methods
    List<Resource> findByProject_ProjectId(Integer projectId);
}
