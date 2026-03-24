package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.project.MilestoneCreateRequest;
import com.cognizant.greencity.dto.project.MilestoneResponse;
import com.cognizant.greencity.dto.project.MilestoneUpdateRequest;
import com.cognizant.greencity.entity.Milestone;
import com.cognizant.greencity.service.MilestoneService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/milestones")
public class MilestoneController {

    private final MilestoneService milestoneService;

    public MilestoneController(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    @GetMapping
    public List<MilestoneResponse> list(@PathVariable Integer projectId, Authentication authentication) {
        return milestoneService.list(projectId, authentication).stream()
                .map(MilestoneController::toResponse)
                .toList();
    }

    @GetMapping("/{milestoneId}")
    public MilestoneResponse get(@PathVariable Integer projectId,
                                    @PathVariable Integer milestoneId,
                                    Authentication authentication) {
        return toResponse(milestoneService.get(projectId, milestoneId, authentication));
    }

    @PostMapping
    public MilestoneResponse create(@PathVariable Integer projectId,
                                      @Valid @RequestBody MilestoneCreateRequest request,
                                      Authentication authentication) {
        return toResponse(milestoneService.create(projectId, request, authentication));
    }

    @PutMapping("/{milestoneId}")
    public MilestoneResponse update(@PathVariable Integer projectId,
                                      @PathVariable Integer milestoneId,
                                      @Valid @RequestBody MilestoneUpdateRequest request,
                                      Authentication authentication) {
        return toResponse(milestoneService.update(projectId, milestoneId, request, authentication));
    }

    @DeleteMapping("/{milestoneId}")
    public void delete(@PathVariable Integer projectId,
                        @PathVariable Integer milestoneId,
                        Authentication authentication) {
        milestoneService.delete(projectId, milestoneId, authentication);
    }

    private static MilestoneResponse toResponse(Milestone milestone) {
        return new MilestoneResponse(
                milestone.getMilestoneId(),
                milestone.getProject() != null ? milestone.getProject().getProjectId() : null,
                milestone.getTitle(),
                milestone.getDate(),
                milestone.getStatus()
        );
    }
}

