package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.project.ImpactCreateRequest;
import com.cognizant.greencity.dto.project.ImpactUpdateRequest;
import com.cognizant.greencity.entity.Impact;
import com.cognizant.greencity.entity.Project;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.exception.UnauthorizedException;
import com.cognizant.greencity.repository.ImpactRepository;
import com.cognizant.greencity.repository.ProjectRepository;
import com.cognizant.greencity.repository.UserRepository;
import com.cognizant.greencity.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ImpactService {

    private final ImpactRepository impactRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    public ImpactService(ImpactRepository impactRepository,
                           ProjectRepository projectRepository,
                           UserRepository userRepository,
                           AuditLogService auditLogService) {
        this.impactRepository = impactRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.auditLogService = auditLogService;
    }

    public List<Impact> list(Integer projectId, Authentication authentication) {
        enforceProjectOwner(projectId, authentication);
        return impactRepository.findByProject_ProjectId(projectId);
    }

    public Impact get(Integer projectId, Integer impactId, Authentication authentication) {
        enforceProjectOwner(projectId, authentication);
        return impactRepository.findByImpactIdAndProject_ProjectId(impactId, projectId)
                .orElseThrow(() -> new NotFoundException("Impact not found"));
    }

    public Impact create(Integer projectId, ImpactCreateRequest request, Authentication authentication) {
        User user = enforceProjectOwner(projectId, authentication);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));

        Impact impact = new Impact();
        impact.setProject(project);
        impact.setDate(request.getDate() != null ? request.getDate() : LocalDateTime.now());
        impact.setMetricsJson(request.getMetricsJson());
        impact.setStatus(request.getStatus());

        Impact saved = impactRepository.save(impact);
        auditLogService.record(user, "IMPACT_CREATE", "projects/" + projectId + "/impacts/" + saved.getImpactId());
        return saved;
    }

    public Impact update(Integer projectId, Integer impactId, ImpactUpdateRequest request, Authentication authentication) {
        User user = enforceProjectOwner(projectId, authentication);
        Impact impact = get(projectId, impactId, authentication);

        if (request.getDate() != null) impact.setDate(request.getDate());
        if (request.getMetricsJson() != null) impact.setMetricsJson(request.getMetricsJson());
        if (request.getStatus() != null) impact.setStatus(request.getStatus());

        Impact saved = impactRepository.save(impact);
        auditLogService.record(user, "IMPACT_UPDATE", "projects/" + projectId + "/impacts/" + impactId);
        return saved;
    }

    public void delete(Integer projectId, Integer impactId, Authentication authentication) {
        User user = enforceProjectOwner(projectId, authentication);
        Impact impact = get(projectId, impactId, authentication);
        impactRepository.delete(impact);
        auditLogService.record(user, "IMPACT_DELETE", "projects/" + projectId + "/impacts/" + impactId);
    }

    private User enforceProjectOwner(Integer projectId, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            throw new UnauthorizedException("Unauthorized");
        }

        User user = userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));

        if (project.getCreatedBy() == null || project.getCreatedBy().getUserId() == null || !user.getUserId().equals(project.getCreatedBy().getUserId())) {
            throw new UnauthorizedException("Not allowed");
        }
        return user;
    }
}

