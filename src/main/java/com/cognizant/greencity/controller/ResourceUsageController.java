package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.resourceusage.ResourceUsageCreateRequest;
import com.cognizant.greencity.dto.resourceusage.ResourceUsageResponse;
import com.cognizant.greencity.dto.resourceusage.ResourceUsageUpdateRequest;
import com.cognizant.greencity.service.ResourceUsageService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources/{resourceId}/usage")
public class ResourceUsageController {

    private final ResourceUsageService resourceUsageService;

    public ResourceUsageController(ResourceUsageService resourceUsageService) {
        this.resourceUsageService = resourceUsageService;
    }

    @GetMapping
    public List<ResourceUsageResponse> list(@PathVariable Integer resourceId) {
        return resourceUsageService.listByResource(resourceId);
    }

    @GetMapping("/{usageId}")
    public ResourceUsageResponse get(@PathVariable Integer usageId) {
        return resourceUsageService.get(usageId);
    }

    @PostMapping
    public ResourceUsageResponse create(@PathVariable Integer resourceId,
                                        @Valid @RequestBody ResourceUsageCreateRequest request,
                                        Authentication authentication) {
        return resourceUsageService.create(resourceId, request, authentication);
    }

    @PutMapping("/{usageId}")
    public ResourceUsageResponse update(@PathVariable Integer usageId,
                                        @Valid @RequestBody ResourceUsageUpdateRequest request,
                                        Authentication authentication) {
        return resourceUsageService.update(usageId, request, authentication);
    }

    @DeleteMapping("/{usageId}")
    public void delete(@PathVariable Integer usageId, Authentication authentication) {
        resourceUsageService.delete(usageId, authentication);
    }
}

