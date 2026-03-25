package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.project.ProjectCreateRequest;
import com.cognizant.greencity.dto.project.ProjectResponse;
import com.cognizant.greencity.dto.project.ProjectUpdateRequest;
import com.cognizant.greencity.service.ProjectService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectResponse> listMine(Authentication authentication) {
        logger.info("Received request to list user's projects");
        List<ProjectResponse> response = projectService.listMine(authentication);
        logger.info("Successfully fetched user's projects");
        return response;
    }

    @GetMapping("/{projectId}")
    public ProjectResponse getMine(@PathVariable Integer projectId, Authentication authentication) {
        logger.info("Received request to get projectId: {}", projectId);
        ProjectResponse response = projectService.getMine(projectId, authentication);
        logger.info("Successfully fetched projectId: {}", projectId);
        return response;
    }

    @PostMapping
    public ProjectResponse create(@Valid @RequestBody ProjectCreateRequest request, Authentication authentication) {
        logger.info("Received request to create a new project");
        ProjectResponse response = projectService.create(request, authentication);
        logger.info("Successfully created a new project");
        return response;
    }

    @PutMapping("/{projectId}")
    public ProjectResponse update(@PathVariable Integer projectId,
                                  @Valid @RequestBody ProjectUpdateRequest request,
                                  Authentication authentication) {
        logger.info("Received request to update projectId: {}", projectId);
        ProjectResponse response = projectService.update(projectId, request, authentication);
        logger.info("Successfully updated projectId: {}", projectId);
        return response;
    }

    @DeleteMapping("/{projectId}")
    public void delete(@PathVariable Integer projectId, Authentication authentication) {
        logger.info("Received request to delete projectId: {}", projectId);
        projectService.delete(projectId, authentication);
        logger.info("Successfully deleted projectId: {}", projectId);
    }
}