package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.resourceusage.ResourceUsageCreateRequest;
import com.cognizant.greencity.dto.resourceusage.ResourceUsageUpdateRequest;
import com.cognizant.greencity.entity.Resource;
import com.cognizant.greencity.entity.ResourceUsage;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.repository.ResourceRepository;
import com.cognizant.greencity.repository.ResourceUsageRepository;
import com.cognizant.greencity.repository.UserRepository;
import com.cognizant.greencity.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResourceUsageService {

    private final ResourceUsageRepository usageRepository;
    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    public ResourceUsageService(ResourceUsageRepository usageRepository,
                                ResourceRepository resourceRepository,
                                UserRepository userRepository,
                                AuditLogService auditLogService) {
        this.usageRepository = usageRepository;
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
        this.auditLogService = auditLogService;
    }

    public List<ResourceUsage> listByResource(Integer resourceId) {
        return usageRepository.findByResource_ResourceId(resourceId);
    }

    public ResourceUsage get(Integer id) {
        return usageRepository.findById(id).orElseThrow(() -> new NotFoundException("Resource usage not found"));
    }

    public ResourceUsage create(Integer resourceId, ResourceUsageCreateRequest request, Authentication authentication) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new NotFoundException("Resource not found"));

        ResourceUsage usage = new ResourceUsage();
        usage.setResource(resource);
        usage.setQuantity(request.getQuantity());
        usage.setStatus(request.getStatus() != null ? request.getStatus() : "RECORDED");
        usage.setDate(LocalDateTime.now());

        ResourceUsage saved = usageRepository.save(usage);
        auditLogService.record(currentUser(authentication), "RESOURCE_USAGE_CREATE", "resources/" + resourceId + "/usage/" + saved.getUsageId());
        return saved;
    }

    public ResourceUsage update(Integer usageId, ResourceUsageUpdateRequest request, Authentication authentication) {
        ResourceUsage usage = get(usageId);

        if (request.getQuantity() != null) usage.setQuantity(request.getQuantity());
        if (request.getStatus() != null) usage.setStatus(request.getStatus());

        ResourceUsage saved = usageRepository.save(usage);
        Integer resourceId = usage.getResource() != null ? usage.getResource().getResourceId() : null;
        auditLogService.record(currentUser(authentication), "RESOURCE_USAGE_UPDATE", "resources/" + resourceId + "/usage/" + usageId);
        return saved;
    }

    public void delete(Integer usageId, Authentication authentication) {
        ResourceUsage usage = get(usageId);
        Integer resourceId = usage.getResource() != null ? usage.getResource().getResourceId() : null;
        usageRepository.delete(usage);
        auditLogService.record(currentUser(authentication), "RESOURCE_USAGE_DELETE", "resources/" + resourceId + "/usage/" + usageId);
    }

    private User currentUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            return null;
        }
        return userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}

