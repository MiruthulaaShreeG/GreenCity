package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.resource.ResourceCreateRequest;
import com.cognizant.greencity.dto.resource.ResourceUpdateRequest;
import com.cognizant.greencity.entity.Project;
import com.cognizant.greencity.entity.Resource;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.repository.ProjectRepository;
import com.cognizant.greencity.repository.ResourceRepository;
import com.cognizant.greencity.repository.UserRepository;
import com.cognizant.greencity.security.UserPrincipal;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@RequiredArgsConstructor
@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

//    public ResourceService(ResourceRepository resourceRepository,
//                           ProjectRepository projectRepository,
//                           UserRepository userRepository,
//                           AuditLogService auditLogService) {
//        this.resourceRepository = resourceRepository;
//        this.projectRepository = projectRepository;
//        this.userRepository = userRepository;
//        this.auditLogService = auditLogService;
//    }

    public List<Resource> list(Integer projectId) {
        if (projectId != null) {
            return resourceRepository.findByProject_ProjectId(projectId);
        }
        return resourceRepository.findAll();
    }

    public Resource get(Integer id) {
        return resourceRepository.findById(id).orElseThrow(() -> new NotFoundException("Resource not found"));
    }

    public Resource create(ResourceCreateRequest request, Authentication authentication) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new NotFoundException("Project not found"));

        Resource resource = new Resource();
        resource.setProject(project);
        resource.setType(request.getType());
        resource.setLocation(request.getLocation());
        resource.setCapacity(request.getCapacity());
        resource.setStatus(request.getStatus() != null ? request.getStatus() : "ACTIVE");

        Resource saved = resourceRepository.save(resource);
        auditLogService.record(currentUser(authentication), "RESOURCE_CREATE", "resources/" + saved.getResourceId());
        return saved;
    }

    public Resource update(Integer id, ResourceUpdateRequest request, Authentication authentication) {
        Resource resource = get(id);

        if (request.getType() != null) resource.setType(request.getType());
        if (request.getLocation() != null) resource.setLocation(request.getLocation());
        if (request.getCapacity() != null) resource.setCapacity(request.getCapacity());
        if (request.getStatus() != null) resource.setStatus(request.getStatus());

        Resource saved = resourceRepository.save(resource);
        auditLogService.record(currentUser(authentication), "RESOURCE_UPDATE", "resources/" + id);
        return saved;
    }

    public void delete(Integer id, Authentication authentication) {
        Resource resource = get(id);
        resourceRepository.delete(resource);
        auditLogService.record(currentUser(authentication), "RESOURCE_DELETE", "resources/" + id);
    }

    private User currentUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            return null;
        }
        return userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}

