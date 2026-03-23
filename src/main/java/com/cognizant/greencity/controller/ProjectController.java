package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.project.ProjectCreateRequest;
import com.cognizant.greencity.dto.project.ProjectResponse;
import com.cognizant.greencity.dto.project.ProjectUpdateRequest;
import com.cognizant.greencity.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectResponse> listMine(Authentication authentication) {
        return projectService.listMine(authentication);
    }

    @GetMapping("/{projectId}")
    public ProjectResponse getMine(@PathVariable Integer projectId, Authentication authentication) {
        return projectService.getMine(projectId, authentication);
    }

    @PostMapping
    public ProjectResponse create(@Valid @RequestBody ProjectCreateRequest request, Authentication authentication) {
        return projectService.create(request, authentication);
    }

    @PutMapping("/{projectId}")
    public ProjectResponse update(@PathVariable Integer projectId,
                                    @Valid @RequestBody ProjectUpdateRequest request,
                                    Authentication authentication) {
        return projectService.update(projectId, request, authentication);
    }

    @DeleteMapping("/{projectId}")
    public void delete(@PathVariable Integer projectId, Authentication authentication) {
        projectService.delete(projectId, authentication);
    }
}

