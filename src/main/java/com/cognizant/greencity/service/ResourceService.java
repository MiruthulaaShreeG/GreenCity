package com.cognizant.greencity.service;

import com.cognizant.greencity.entity.Resource;
import com.cognizant.greencity.entity.AuditLog;
import com.cognizant.greencity.dao.AuditLogRepository;
import com.cognizant.greencity.exception.ResourceNotFoundException;
import com.cognizant.greencity.dao.ResourceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final AuditLogRepository auditLogRepository;

    public ResourceService(ResourceRepository resourceRepository, AuditLogRepository auditLogRepository) {
        this.resourceRepository = resourceRepository;
        this.auditLogRepository = auditLogRepository;
    }

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public Resource getResourceById(UUID id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with ID: " + id));
    }

    public Resource createResource(Resource resource, UUID userId) {
        Resource saved = resourceRepository.save(resource);
        logAudit(userId, "CREATE", "Resource", null, saved.toString());
        return saved;
    }

    public Resource updateResource(UUID id, Resource updatedResource, UUID userId) {
        return resourceRepository.findById(id).map(resource -> {
            String oldValue = resource.toString();
            resource.setType(updatedResource.getType());
            resource.setLocation(updatedResource.getLocation());
            resource.setCapacity(updatedResource.getCapacity());
            resource.setStatus(updatedResource.getStatus());
            Resource saved = resourceRepository.save(resource);
            logAudit(userId, "UPDATE", "Resource", oldValue, saved.toString());
            return saved;
        }).orElseThrow(() -> new ResourceNotFoundException("Resource not found with ID: " + id));
    }

    public void deleteResource(UUID id, UUID userId) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with ID: " + id));
        resourceRepository.deleteById(id);
        logAudit(userId, "DELETE", "Resource", resource.toString(), null);
    }

    private void logAudit(UUID userId, String action, String entity, String oldValue, String newValue) {
        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setAction(action);
        log.setEntity(entity);
        log.setOldValue(oldValue);
        log.setNewValue(newValue);
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }


}