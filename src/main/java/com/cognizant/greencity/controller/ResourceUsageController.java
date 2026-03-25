package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.resourceusage.ResourceUsageCreateRequest;
import com.cognizant.greencity.dto.resourceusage.ResourceUsageResponse;
import com.cognizant.greencity.dto.resourceusage.ResourceUsageUpdateRequest;
import com.cognizant.greencity.service.ResourceUsageService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources/{resourceId}/usage")
public class ResourceUsageController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceUsageController.class);
    private final ResourceUsageService resourceUsageService;

    public ResourceUsageController(ResourceUsageService resourceUsageService) {
        this.resourceUsageService = resourceUsageService;
    }

    @GetMapping
    public List<ResourceUsageResponse> list(@PathVariable Integer resourceId) {
        logger.info("Received request to list resource usage for resourceId: {}", resourceId);
        List<ResourceUsageResponse> response = resourceUsageService.listByResource(resourceId);
        logger.info("Successfully fetched resource usage for resourceId: {}", resourceId);
        return response;
    }

    @GetMapping("/{usageId}")
    public ResourceUsageResponse get(@PathVariable Integer usageId) {
        logger.info("Received request to get resource usage id: {}", usageId);
        ResourceUsageResponse response = resourceUsageService.get(usageId);
        logger.info("Successfully fetched resource usage id: {}", usageId);
        return response;
    }

    @PostMapping
    public ResourceUsageResponse create(@PathVariable Integer resourceId,
                                        @Valid @RequestBody ResourceUsageCreateRequest request,
                                        Authentication authentication) {
        logger.info("Received request to create resource usage for resourceId: {}", resourceId);
        ResourceUsageResponse response = resourceUsageService.create(resourceId, request, authentication);
        logger.info("Successfully created resource usage for resourceId: {}", resourceId);
        return response;
    }

    @PutMapping("/{usageId}")
    public ResourceUsageResponse update(@PathVariable Integer usageId,
                                        @Valid @RequestBody ResourceUsageUpdateRequest request,
                                        Authentication authentication) {
        logger.info("Received request to update resource usage id: {}", usageId);
        ResourceUsageResponse response = resourceUsageService.update(usageId, request, authentication);
        logger.info("Successfully updated resource usage id: {}", usageId);
        return response;
    }

    @DeleteMapping("/{usageId}")
    public void delete(@PathVariable Integer usageId, Authentication authentication) {
        logger.info("Received request to delete resource usage id: {}", usageId);
        resourceUsageService.delete(usageId, authentication);
        logger.info("Successfully deleted resource usage id: {}", usageId);
    }
}