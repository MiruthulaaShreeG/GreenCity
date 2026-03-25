package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.report.CitizenReportCreateRequest;
import com.cognizant.greencity.dto.report.CitizenReportResponse;
import com.cognizant.greencity.dto.report.CitizenReportUpdateRequest;
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
        return citizenReportService.listMine(authentication);
    }

    @GetMapping("/{id}")
    public CitizenReportResponse getMine(@PathVariable Integer id, Authentication authentication) {
        return citizenReportService.getMine(id, authentication);
    }

    @PostMapping
    public CitizenReportResponse create(@Valid @RequestBody CitizenReportCreateRequest request, Authentication authentication) {
        return citizenReportService.create(request, authentication);
    }

    @PutMapping("/{id}")
    public CitizenReportResponse update(@PathVariable Integer id, @Valid @RequestBody CitizenReportUpdateRequest request, Authentication authentication) {
        return citizenReportService.update(id, request, authentication);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id, Authentication authentication) {
        citizenReportService.delete(id, authentication);
    }
}

