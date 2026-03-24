package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.FundUtilizationDTO;
import com.cognizant.greencity.entity.FundUtilization;
import com.cognizant.greencity.service.FundUtilizationService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/funds")
public class FundUtilizationController {

    private final FundUtilizationService fundService;

    public FundUtilizationController(FundUtilizationService fundService) {
        this.fundService = fundService;
    }

    // Auditor & normal users can view
    @GetMapping("/daily/{date}")
    public List<FundUtilization> getDailyFunds(@PathVariable String date) {
        return fundService.getDailyFundUtilization(LocalDate.parse(date));
    }

    // Restricted to non-auditors
    @PostMapping
    public FundUtilization createFund(@RequestBody FundUtilizationDTO dto,
                                      @RequestHeader("userId") UUID userId,
                                      @RequestHeader("role") String role) {
        if ("AUDITOR".equalsIgnoreCase(role)) {
            throw new SecurityException("Auditors have read-only access");
        }
        FundUtilization fund = new FundUtilization();
        fund.setAllocatedAmount(dto.getAllocatedAmount());
        fund.setUsedAmount(dto.getUsedAmount());
        fund.setRemainingAmount(dto.getAllocatedAmount() - dto.getUsedAmount());
        fund.setPurpose(dto.getPurpose());
        fund.setDate(dto.getDate());
        fund.setStatus(dto.getStatus());
        return fundService.createFundUtilization(fund, userId);
    }

    @PutMapping("/{id}")
    public FundUtilization updateFund(@PathVariable UUID id,
                                      @RequestBody FundUtilizationDTO dto,
                                      @RequestHeader("userId") UUID userId,
                                      @RequestHeader("role") String role) {
        if ("AUDITOR".equalsIgnoreCase(role)) {
            throw new SecurityException("Auditors have read-only access");
        }
        FundUtilization updated = new FundUtilization();
        updated.setAllocatedAmount(dto.getAllocatedAmount());
        updated.setUsedAmount(dto.getUsedAmount());
        updated.setPurpose(dto.getPurpose());
        updated.setDate(dto.getDate());
        updated.setStatus(dto.getStatus());
        return fundService.updateFundUtilization(id, updated, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteFund(@PathVariable UUID id,
                           @RequestHeader("userId") UUID userId,
                           @RequestHeader("role") String role) {
        if ("AUDITOR".equalsIgnoreCase(role)) {
            throw new SecurityException("Auditors have read-only access");
        }
        fundService.deleteFundUtilization(id, userId);
    }
}
