package com.cognizant.greencity.service.impl;

import com.cognizant.greencity.dao.AuditRepository;
import com.cognizant.greencity.dao.ComplianceRecordRepository;
import com.cognizant.greencity.dao.UserRepository;
import com.cognizant.greencity.dto.AuditDTO;
import com.cognizant.greencity.entity.Audit;
import com.cognizant.greencity.entity.ComplianceRecord;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditServiceImpl implements AuditService {
    @Autowired private UserRepository userRepository;
    @Autowired private ComplianceRecordRepository complianceRecordRepository;
    @Autowired private AuditRepository auditRepository;
    @Override
    public AuditDTO createAudit(AuditDTO request) {
        User officer = userRepository.findById(request.getOfficerId())
                .orElseThrow(() -> new RuntimeException("Officer not found"));

        ComplianceRecord record = complianceRecordRepository.findById(request.getComplianceRecordId())
                .orElseThrow(() -> new RuntimeException("Compliance record not found"));

        Audit audit = new Audit();
        audit.setOfficer(officer);
        audit.setComplianceRecord(record);
        audit.setScope(request.getScope());
        audit.setFindings(request.getFindings());
        audit.setStatus(request.getStatus());
        audit.setDate(LocalDateTime.now());

       Audit savedaudit =  auditRepository.save(audit);
        return mapTODTO(savedaudit);
    }
    private AuditDTO mapTODTO(Audit savedaudit) {
        AuditDTO auditDTO = new AuditDTO();
        auditDTO.setAuditId(savedaudit.getAuditId());
        auditDTO.setOfficerId(savedaudit.getOfficer().getUserId());
        auditDTO.setComplianceRecordId(savedaudit.getComplianceRecord().getComplianceId());
        auditDTO.setScope(savedaudit.getScope());
        auditDTO.setFindings(savedaudit.getFindings());
        auditDTO.setStatus(savedaudit.getStatus());
        auditDTO.setDate(savedaudit.getDate());
        return auditDTO;
    }
}
