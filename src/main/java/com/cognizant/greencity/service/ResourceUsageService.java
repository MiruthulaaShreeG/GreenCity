package com.cognizant.greencity.service;

import com.cognizant.greencity.entity.Resource;
import com.cognizant.greencity.entity.ResourceUsage;
import com.cognizant.greencity.exception.ResourceUsageNotFoundException;
import com.cognizant.greencity.dao.ResourceRepository;
import com.cognizant.greencity.dao.ResourceUsageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ResourceUsageService {

    private final ResourceUsageRepository usageRepository;
    private final ResourceRepository resourceRepository;

    public ResourceUsageService(ResourceUsageRepository usageRepository,
                                ResourceRepository resourceRepository) {
        this.usageRepository = usageRepository;
        this.resourceRepository = resourceRepository;
    }

    public List<ResourceUsage> getAllUsage() {
        return usageRepository.findAll();
    }

    public ResourceUsage getUsageById(UUID id) {
        return usageRepository.findById(id)
                .orElseThrow(() -> new ResourceUsageNotFoundException("Usage record not found with ID: " + id));
    }

    public ResourceUsage createUsage(UUID resourceId, ResourceUsage usage) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceUsageNotFoundException("Resource not found with ID: " + resourceId));
        usage.setResource(resource);
        return usageRepository.save(usage);
    }

    public ResourceUsage updateUsage(UUID id, ResourceUsage updatedUsage) {
        return usageRepository.findById(id).map(usage -> {
            usage.setQuantity(updatedUsage.getQuantity());
            usage.setDate(updatedUsage.getDate());
            usage.setStatus(updatedUsage.getStatus());
            return usageRepository.save(usage);
        }).orElseThrow(() -> new ResourceUsageNotFoundException("Usage record not found with ID: " + id));
    }

    public void deleteUsage(UUID id) {
        if (!usageRepository.existsById(id)) {
            throw new ResourceUsageNotFoundException("Cannot delete. Usage record not found with ID: " + id);
        }
        usageRepository.deleteById(id);
    }

    public List<ResourceUsage> getUsageByDateRange(LocalDateTime start, LocalDateTime end) {
        return usageRepository.findByDateBetween(start, end);
    }
}
