package com.cognizant.greencity.service;

import com.cognizant.greencity.entity.FundUtilization;
import com.cognizant.greencity.entity.AuditLog;
import com.cognizant.greencity.dao.FundUtilizationRepository;
import com.cognizant.greencity.dao.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FundUtilizationService {

    private final FundUtilizationRepository fundRepo;
    private final AuditLogRepository auditRepo;

    public FundUtilizationService(FundUtilizationRepository fundRepo, AuditLogRepository auditRepo) {
        this.fundRepo = fundRepo;
        this.auditRepo = auditRepo;
    }

    public List<FundUtilization> getDailyFundUtilization(LocalDate date) {
        return fundRepo.findByDate(date);
    }

    public FundUtilization createFundUtilization(FundUtilization fund, UUID userId) {
        fund.setRemainingAmount(fund.getAllocatedAmount() - fund.getUsedAmount());
        FundUtilization saved = fundRepo.save(fund);
        logAudit(userId, "CREATE", "FundUtilization", null, saved.toString());
        return saved;
    }

    public FundUtilization updateFundUtilization(UUID id, FundUtilization updated, UUID userId) {
        return fundRepo.findById(id).map(existing -> {
            String oldValue = existing.toString();
            existing.setAllocatedAmount(updated.getAllocatedAmount());
            existing.setUsedAmount(updated.getUsedAmount());
            existing.setRemainingAmount(updated.getAllocatedAmount() - updated.getUsedAmount());
            existing.setPurpose(updated.getPurpose());
            existing.setDate(updated.getDate());
            existing.setStatus(updated.getStatus());
            FundUtilization saved = fundRepo.save(existing);
            logAudit(userId, "UPDATE", "FundUtilization", oldValue, saved.toString());
            return saved;
        }).orElseThrow(() -> new RuntimeException("FundUtilization not found with ID: " + id));
    }

    public void deleteFundUtilization(UUID id, UUID userId) {
        FundUtilization existing = fundRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("FundUtilization not found with ID: " + id));
        fundRepo.deleteById(id);
        logAudit(userId, "DELETE", "FundUtilization", existing.toString(), null);
    }

    private void logAudit(UUID userId, String action, String entity, String oldValue, String newValue) {
        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setAction(action);
        log.setEntity(entity);
        log.setOldValue(oldValue);
        log.setNewValue(newValue);
        log.setTimestamp(LocalDateTime.now());
        auditRepo.save(log);
    }
}
