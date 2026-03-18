package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.project.MilestoneCreateRequest;
import com.cognizant.greencity.dto.project.MilestoneUpdateRequest;
import com.cognizant.greencity.entity.Milestone;
import com.cognizant.greencity.entity.Project;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.exception.UnauthorizedException;
import com.cognizant.greencity.repository.MilestoneRepository;
import com.cognizant.greencity.repository.ProjectRepository;
import com.cognizant.greencity.repository.UserRepository;
import com.cognizant.greencity.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    public MilestoneService(MilestoneRepository milestoneRepository,
                             ProjectRepository projectRepository,
                             UserRepository userRepository,
                             AuditLogService auditLogService) {
        this.milestoneRepository = milestoneRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.auditLogService = auditLogService;
    }

    public List<Milestone> list(Integer projectId, Authentication authentication) {
        enforceProjectOwner(projectId, authentication);
        return milestoneRepository.findByProject_ProjectId(projectId);
    }

    public Milestone get(Integer projectId, Integer milestoneId, Authentication authentication) {
        enforceProjectOwner(projectId, authentication);
        return milestoneRepository.findByMilestoneIdAndProject_ProjectId(milestoneId, projectId)
                .orElseThrow(() -> new NotFoundException("Milestone not found"));
    }

    public Milestone create(Integer projectId, MilestoneCreateRequest request, Authentication authentication) {
        User user = enforceProjectOwner(projectId, authentication);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));

        Milestone milestone = new Milestone();
        milestone.setProject(project);
        milestone.setTitle(request.getTitle());
        milestone.setDate(request.getDate() != null ? request.getDate() : LocalDate.now());
        milestone.setStatus(request.getStatus());

        Milestone saved = milestoneRepository.save(milestone);
        auditLogService.record(user, "MILESTONE_CREATE", "projects/" + projectId + "/milestones/" + saved.getMilestoneId());
        return saved;
    }

    public Milestone update(Integer projectId, Integer milestoneId, MilestoneUpdateRequest request, Authentication authentication) {
        User user = enforceProjectOwner(projectId, authentication);
        Milestone milestone = get(projectId, milestoneId, authentication);

        if (request.getTitle() != null) milestone.setTitle(request.getTitle());
        if (request.getDate() != null) milestone.setDate(request.getDate());
        if (request.getStatus() != null) milestone.setStatus(request.getStatus());

        Milestone saved = milestoneRepository.save(milestone);
        auditLogService.record(user, "MILESTONE_UPDATE", "projects/" + projectId + "/milestones/" + milestoneId);
        return saved;
    }

    public void delete(Integer projectId, Integer milestoneId, Authentication authentication) {
        User user = enforceProjectOwner(projectId, authentication);
        Milestone milestone = get(projectId, milestoneId, authentication);
        milestoneRepository.delete(milestone);
        auditLogService.record(user, "MILESTONE_DELETE", "projects/" + projectId + "/milestones/" + milestoneId);
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

