package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.resourceusage.ResourceUsageCreateRequest;
import com.cognizant.greencity.dto.resourceusage.ResourceUsageResponse;
import com.cognizant.greencity.dto.resourceusage.ResourceUsageUpdateRequest;
import com.cognizant.greencity.entity.Resource;
import com.cognizant.greencity.entity.ResourceUsage;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.repository.ResourceRepository;
import com.cognizant.greencity.repository.ResourceUsageRepository;
import com.cognizant.greencity.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceUsageService {

    private final ResourceUsageRepository usageRepository;
    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;
    private final ModelMapper modelMapper;

    public List<ResourceUsageResponse> listByResource(Integer resourceId) {
        return usageRepository.findByResource_ResourceId(resourceId).stream().map(this::toResponse).toList();
    }

    public ResourceUsageResponse get(Integer id) {
        return toResponse(getEntity(id));
    }

    public ResourceUsageResponse create(Integer resourceId, ResourceUsageCreateRequest request, Authentication authentication) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new NotFoundException("Resource not found"));

        ResourceUsage usage = new ResourceUsage();
        usage.setResource(resource);
        usage.setQuantity(request.getQuantity());
        usage.setStatus(request.getStatus() != null ? request.getStatus() : "RECORDED");
        usage.setDate(LocalDateTime.now());

        ResourceUsage saved = usageRepository.save(usage);
        auditLogService.record(currentUser(authentication), "RESOURCE_USAGE_CREATE", "resources/" + resourceId + "/usage/" + saved.getUsageId());
        return toResponse(saved);
    }

    public ResourceUsageResponse update(Integer usageId, ResourceUsageUpdateRequest request, Authentication authentication) {
        ResourceUsage usage = getEntity(usageId);

        if (request.getQuantity() != null) usage.setQuantity(request.getQuantity());
        if (request.getStatus() != null) usage.setStatus(request.getStatus());

        ResourceUsage saved = usageRepository.save(usage);
        Integer resourceId = usage.getResource() != null ? usage.getResource().getResourceId() : null;
        auditLogService.record(currentUser(authentication), "RESOURCE_USAGE_UPDATE", "resources/" + resourceId + "/usage/" + usageId);
        return toResponse(saved);
    }

    public void delete(Integer usageId, Authentication authentication) {
        ResourceUsage usage = getEntity(usageId);
        Integer resourceId = usage.getResource() != null ? usage.getResource().getResourceId() : null;
        usageRepository.delete(usage);
        auditLogService.record(currentUser(authentication), "RESOURCE_USAGE_DELETE", "resources/" + resourceId + "/usage/" + usageId);
    }

    private ResourceUsage getEntity(Integer id) {
        return usageRepository.findById(id).orElseThrow(() -> new NotFoundException("Resource usage not found"));
    }

    private ResourceUsageResponse toResponse(ResourceUsage usage) {
        ResourceUsageResponse response = modelMapper.map(usage, ResourceUsageResponse.class);
        response.setResourceId(usage.getResource() != null ? usage.getResource().getResourceId() : null);
        return response;
    }

    private User currentUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            return null;
        }
        return userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}

