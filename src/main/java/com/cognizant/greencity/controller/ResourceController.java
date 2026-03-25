package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.resource.ResourceCreateRequest;
import com.cognizant.greencity.dto.resource.ResourceResponse;
import com.cognizant.greencity.dto.resource.ResourceUpdateRequest;
import com.cognizant.greencity.service.ResourceService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public List<ResourceResponse> list(@RequestParam(value = "projectId", required = false) Integer projectId) {
        logger.info("Received request to list resources (projectId: {})", projectId);
        List<ResourceResponse> response = resourceService.list(projectId);
        logger.info("Successfully fetched resources");
        return response;
    }

    @GetMapping("/{id}")
    public ResourceResponse get(@PathVariable Integer id) {
        logger.info("Received request to get resource id: {}", id);
        ResourceResponse response = resourceService.get(id);
        logger.info("Successfully fetched resource id: {}", id);
        return response;
    }

    @PostMapping
    public ResourceResponse create(@Valid @RequestBody ResourceCreateRequest request, Authentication authentication) {
        logger.info("Received request to create a resource");
        ResourceResponse response = resourceService.create(request, authentication);
        logger.info("Successfully created a resource");
        return response;
    }

    @PutMapping("/{id}")
    public ResourceResponse update(@PathVariable Integer id,
                                   @Valid @RequestBody ResourceUpdateRequest request,
                                   Authentication authentication) {
        logger.info("Received request to update resource id: {}", id);
        ResourceResponse response = resourceService.update(id, request, authentication);
        logger.info("Successfully updated resource id: {}", id);
        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id, Authentication authentication) {
        logger.info("Received request to delete resource id: {}", id);
        resourceService.delete(id, authentication);
        logger.info("Successfully deleted resource id: {}", id);
    }
}