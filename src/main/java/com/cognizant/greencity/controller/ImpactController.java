package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.project.ImpactCreateRequest;
import com.cognizant.greencity.dto.project.ImpactResponse;
import com.cognizant.greencity.dto.project.ImpactUpdateRequest;
import com.cognizant.greencity.service.ImpactService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/impacts")
public class ImpactController {

    private static final Logger logger = LoggerFactory.getLogger(ImpactController.class);
    private final ImpactService impactService;

    public ImpactController(ImpactService impactService) {
        this.impactService = impactService;
    }

    @GetMapping
    public List<ImpactResponse> list(@PathVariable Integer projectId, Authentication authentication) {
        logger.info("Received request to list impacts for projectId: {}", projectId);
        List<ImpactResponse> response = impactService.list(projectId, authentication);
        logger.info("Successfully fetched impacts for projectId: {}", projectId);
        return response;
    }

    @GetMapping("/{impactId}")
    public ImpactResponse get(@PathVariable Integer projectId,
                              @PathVariable Integer impactId,
                              Authentication authentication) {
        logger.info("Received request to get impactId: {} for projectId: {}", impactId, projectId);
        ImpactResponse response = impactService.get(projectId, impactId, authentication);
        logger.info("Successfully fetched impactId: {} for projectId: {}", impactId, projectId);
        return response;
    }

    @PostMapping
    public ImpactResponse create(@PathVariable Integer projectId,
                                 @Valid @RequestBody ImpactCreateRequest request,
                                 Authentication authentication) {
        logger.info("Received request to create impact for projectId: {}", projectId);
        ImpactResponse response = impactService.create(projectId, request, authentication);
        logger.info("Successfully created impact for projectId: {}", projectId);
        return response;
    }

    @PutMapping("/{impactId}")
    public ImpactResponse update(@PathVariable Integer projectId,
                                 @PathVariable Integer impactId,
                                 @Valid @RequestBody ImpactUpdateRequest request,
                                 Authentication authentication) {
        logger.info("Received request to update impactId: {} for projectId: {}", impactId, projectId);
        ImpactResponse response = impactService.update(projectId, impactId, request, authentication);
        logger.info("Successfully updated impactId: {} for projectId: {}", impactId, projectId);
        return response;
    }

    @DeleteMapping("/{impactId}")
    public void delete(@PathVariable Integer projectId,
                       @PathVariable Integer impactId,
                       Authentication authentication) {
        logger.info("Received request to delete impactId: {} for projectId: {}", impactId, projectId);
        impactService.delete(projectId, impactId, authentication);
        logger.info("Successfully deleted impactId: {} for projectId: {}", impactId, projectId);
    }
}