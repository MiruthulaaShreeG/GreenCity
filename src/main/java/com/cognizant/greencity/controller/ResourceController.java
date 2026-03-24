package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.resource.ResourceCreateRequest;
import com.cognizant.greencity.dto.resource.ResourceResponse;
import com.cognizant.greencity.dto.resource.ResourceUpdateRequest;
import com.cognizant.greencity.service.ResourceService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public List<ResourceResponse> list(@RequestParam(value = "projectId", required = false) Integer projectId) {
        return resourceService.list(projectId);
    }

    @GetMapping("/{id}")
    public ResourceResponse get(@PathVariable Integer id) {
        return resourceService.get(id);
    }

    @PostMapping
    public ResourceResponse create(@Valid @RequestBody ResourceCreateRequest request, Authentication authentication) {
        return resourceService.create(request, authentication);
    }

    @PutMapping("/{id}")
    public ResourceResponse update(@PathVariable Integer id,
                                   @Valid @RequestBody ResourceUpdateRequest request,
                                   Authentication authentication) {
        return resourceService.update(id, request, authentication);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id, Authentication authentication) {
        resourceService.delete(id, authentication);
    }
}

