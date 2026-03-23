package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.project.ProjectCreateRequest;
import com.cognizant.greencity.dto.project.ProjectResponse;
import com.cognizant.greencity.dto.project.ProjectUpdateRequest;
import com.cognizant.greencity.entity.Project;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.exception.UnauthorizedException;
import com.cognizant.greencity.repository.ProjectRepository;
import com.cognizant.greencity.repository.UserRepository;
import com.cognizant.greencity.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;
    private final ModelMapper modelMapper;

    public List<ProjectResponse> listMine(Authentication authentication) {
        User user = currentUser(authentication);
        return projectRepository.findByCreatedBy_UserId(user.getUserId()).stream().map(this::toResponse).toList();
    }

    public ProjectResponse getMine(Integer projectId, Authentication authentication) {
        Project project = getEntity(projectId);
        enforceOwner(project, authentication);
        return toResponse(project);
    }

    public ProjectResponse create(ProjectCreateRequest request, Authentication authentication) {
        User user = currentUser(authentication);

        Project project = new Project();
        project.setCreatedBy(user);
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setBudget(request.getBudget());
        project.setStatus(request.getStatus() != null ? request.getStatus() : "ACTIVE");

        Project saved = projectRepository.save(project);
        auditLogService.record(user, "PROJECT_CREATE", "projects/" + saved.getProjectId());
        return toResponse(saved);
    }

    public ProjectResponse update(Integer projectId, ProjectUpdateRequest request, Authentication authentication) {
        Project project = getEntity(projectId);
        enforceOwner(project, authentication);
        if (request.getTitle() != null) project.setTitle(request.getTitle());
        if (request.getDescription() != null) project.setDescription(request.getDescription());
        if (request.getStartDate() != null) project.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) project.setEndDate(request.getEndDate());
        if (request.getBudget() != null) project.setBudget(request.getBudget());
        if (request.getStatus() != null) project.setStatus(request.getStatus());

        Project saved = projectRepository.save(project);
        auditLogService.record(currentUser(authentication), "PROJECT_UPDATE", "projects/" + projectId);
        return toResponse(saved);
    }

    public void delete(Integer projectId, Authentication authentication) {
        Project project = getEntity(projectId);
        enforceOwner(project, authentication);
        User user = currentUser(authentication);
        projectRepository.delete(project);
        auditLogService.record(user, "PROJECT_DELETE", "projects/" + projectId);
    }

    private Project getEntity(Integer projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));
    }

    private ProjectResponse toResponse(Project project) {
        ProjectResponse response = modelMapper.map(project, ProjectResponse.class);
        response.setCreatedBy(project.getCreatedBy() != null ? project.getCreatedBy().getUserId() : null);
        return response;
    }

    private void enforceOwner(Project project, Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        Integer userId = principal.getUserId();
        if (project.getCreatedBy() == null || project.getCreatedBy().getUserId() == null || !userId.equals(project.getCreatedBy().getUserId())) {
            throw new UnauthorizedException("Not allowed");
        }
    }

    private User currentUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            throw new UnauthorizedException("Unauthorized");
        }
        return userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}

