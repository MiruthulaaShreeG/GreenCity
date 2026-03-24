package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.report.CitizenReportCreateRequest;
import com.cognizant.greencity.dto.report.CitizenReportResponse;
import com.cognizant.greencity.dto.report.CitizenReportUpdateRequest;
import com.cognizant.greencity.entity.CitizenReport;
import com.cognizant.greencity.service.CitizenReportService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citizen-reports")
public class CitizenReportController {

    private final CitizenReportService citizenReportService;

    public CitizenReportController(CitizenReportService citizenReportService) {
        this.citizenReportService = citizenReportService;
    }

    @GetMapping
    public List<CitizenReportResponse> listMine(Authentication authentication) {
        return citizenReportService.listMine(authentication).stream().map(CitizenReportController::toResponse).toList();
    }

    @GetMapping("/{id}")
    public CitizenReportResponse getMine(@PathVariable Integer id, Authentication authentication) {
        return toResponse(citizenReportService.getMine(id, authentication));
    }

    @PostMapping
    public CitizenReportResponse create(@Valid @RequestBody CitizenReportCreateRequest request, Authentication authentication) {
        return toResponse(citizenReportService.create(request, authentication));
    }

    @PutMapping("/{id}")
    public CitizenReportResponse update(@PathVariable Integer id, @Valid @RequestBody CitizenReportUpdateRequest request, Authentication authentication) {
        return toResponse(citizenReportService.update(id, request, authentication));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id, Authentication authentication) {
        citizenReportService.delete(id, authentication);
    }

    private static CitizenReportResponse toResponse(CitizenReport report) {
        return new CitizenReportResponse(
                report.getReportID(),
                report.getCitizen() != null ? report.getCitizen().getUserId() : null,
                report.getType(),
                report.getLocation(),
                report.getDate(),
                report.getStatus()
        );
    }
}

