package com.cognizant.greencity.controller;

import com.cognizant.greencity.DTO.ResourceUsageDTO;
import com.cognizant.greencity.entity.Resource;
import com.cognizant.greencity.entity.ResourceUsage;
import com.cognizant.greencity.repository.ResourceRepository;
import com.cognizant.greencity.repository.ResourceUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/resource-usage")
public class ResourceUsageController {

    @Autowired
    private ResourceUsageRepository usageRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @GetMapping
    public List<ResourceUsage> getAllUsage() {
        return usageRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResourceUsage getUsageById(@PathVariable UUID id) {
        return usageRepository.findById(id).orElse(null);
    }

    @PostMapping
    public ResourceUsage createUsage(@RequestBody ResourceUsageDTO dto) {
        Resource resource = resourceRepository.findById(dto.getResourceId()).orElse(null);
        if (resource == null) return null;

        ResourceUsage usage = new ResourceUsage();
        usage.setResource(resource);
        usage.setQuantity(dto.getQuantity());
        usage.setDate(dto.getDate());
        usage.setStatus(dto.getStatus());
        return usageRepository.save(usage);
    }

    @PutMapping("/{id}")
    public ResourceUsage updateUsage(@PathVariable UUID id, @RequestBody ResourceUsageDTO dto) {
        return usageRepository.findById(id).map(usage -> {
            usage.setQuantity(dto.getQuantity());
            usage.setDate(dto.getDate());
            usage.setStatus(dto.getStatus());
            return usageRepository.save(usage);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteUsage(@PathVariable UUID id) {
        usageRepository.deleteById(id);
    }
}
