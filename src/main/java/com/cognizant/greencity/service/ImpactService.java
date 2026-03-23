package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.project.ImpactCreateRequest;
import com.cognizant.greencity.dto.project.ImpactResponse;
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
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImpactService {

    private final ImpactRepository impactRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;
    private final ModelMapper modelMapper;

    public List<ImpactResponse> list(Integer projectId, Authentication authentication) {
        enforceProjectOwner(projectId, authentication);
        return impactRepository.findByProject_ProjectId(projectId).stream().map(this::toResponse).toList();
    }

    public ImpactResponse get(Integer projectId, Integer impactId, Authentication authentication) {
        enforceProjectOwner(projectId, authentication);
        return toResponse(getEntity(projectId, impactId));
    }

    public ImpactResponse create(Integer projectId, ImpactCreateRequest request, Authentication authentication) {
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
        return toResponse(saved);
    }

    public ImpactResponse update(Integer projectId, Integer impactId, ImpactUpdateRequest request, Authentication authentication) {
        User user = enforceProjectOwner(projectId, authentication);
        Impact impact = getEntity(projectId, impactId);

        if (request.getDate() != null) impact.setDate(request.getDate());
        if (request.getMetricsJson() != null) impact.setMetricsJson(request.getMetricsJson());
        if (request.getStatus() != null) impact.setStatus(request.getStatus());

        Impact saved = impactRepository.save(impact);
        auditLogService.record(user, "IMPACT_UPDATE", "projects/" + projectId + "/impacts/" + impactId);
        return toResponse(saved);
    }

    public void delete(Integer projectId, Integer impactId, Authentication authentication) {
        User user = enforceProjectOwner(projectId, authentication);
        Impact impact = getEntity(projectId, impactId);
        impactRepository.delete(impact);
        auditLogService.record(user, "IMPACT_DELETE", "projects/" + projectId + "/impacts/" + impactId);
    }

    private Impact getEntity(Integer projectId, Integer impactId) {
        return impactRepository.findByImpactIdAndProject_ProjectId(impactId, projectId)
                .orElseThrow(() -> new NotFoundException("Impact not found"));
    }

    private ImpactResponse toResponse(Impact impact) {
        ImpactResponse response = modelMapper.map(impact, ImpactResponse.class);
        response.setProjectId(impact.getProject() != null ? impact.getProject().getProjectId() : null);
        return response;
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

