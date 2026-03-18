package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.project.ImpactCreateRequest;
import com.cognizant.greencity.dto.project.ImpactResponse;
import com.cognizant.greencity.dto.project.ImpactUpdateRequest;
import com.cognizant.greencity.entity.Impact;
import com.cognizant.greencity.service.ImpactService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/impacts")
public class ImpactController {

    private final ImpactService impactService;

    public ImpactController(ImpactService impactService) {
        this.impactService = impactService;
    }

    @GetMapping
    public List<ImpactResponse> list(@PathVariable Integer projectId, Authentication authentication) {
        return impactService.list(projectId, authentication).stream()
                .map(ImpactController::toResponse)
                .toList();
    }

    @GetMapping("/{impactId}")
    public ImpactResponse get(@PathVariable Integer projectId,
                                @PathVariable Integer impactId,
                                Authentication authentication) {
        return toResponse(impactService.get(projectId, impactId, authentication));
    }

    @PostMapping
    public ImpactResponse create(@PathVariable Integer projectId,
                                    @Valid @RequestBody ImpactCreateRequest request,
                                    Authentication authentication) {
        return toResponse(impactService.create(projectId, request, authentication));
    }

    @PutMapping("/{impactId}")
    public ImpactResponse update(@PathVariable Integer projectId,
                                    @PathVariable Integer impactId,
                                    @Valid @RequestBody ImpactUpdateRequest request,
                                    Authentication authentication) {
        return toResponse(impactService.update(projectId, impactId, request, authentication));
    }

    @DeleteMapping("/{impactId}")
    public void delete(@PathVariable Integer projectId,
                        @PathVariable Integer impactId,
                        Authentication authentication) {
        impactService.delete(projectId, impactId, authentication);
    }

    private static ImpactResponse toResponse(Impact impact) {
        return new ImpactResponse(
                impact.getImpactId(),
                impact.getProject() != null ? impact.getProject().getProjectId() : null,
                impact.getDate(),
                impact.getMetricsJson(),
                impact.getStatus()
        );
    }
}

