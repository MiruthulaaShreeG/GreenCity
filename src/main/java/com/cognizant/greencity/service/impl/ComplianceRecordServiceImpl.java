package com.cognizant.greencity.service.impl;

import com.cognizant.greencity.dao.ComplianceRecordRepository;
import com.cognizant.greencity.dto.ComplianceRecordDTO;
import com.cognizant.greencity.entity.ComplianceRecord;
import com.cognizant.greencity.service.ComplicanceRecordSercive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplianceRecordServiceImpl implements ComplicanceRecordSercive {

    @Autowired
    private ComplianceRecordRepository complianceRecordRepository;
    @Override
    public ComplianceRecordDTO createComplianceRecord(ComplianceRecordDTO request) {
        // Map DTO to Entity
        ComplianceRecord complianceRecord = new ComplianceRecord();
        complianceRecord.setComplianceId(request.getComplianceId());
        complianceRecord.setEntityType(request.getEntityType());
        complianceRecord.setNotes(request.getNotes());
        complianceRecord.setDate(request.getDate());

        // Save to database
        ComplianceRecord savedRecord = complianceRecordRepository.save(complianceRecord);
        return mapTODTO(savedRecord);

    }
    public ComplianceRecordDTO mapTODTO(ComplianceRecord complianceRecord) {
        ComplianceRecordDTO complianceRecordDTO = new ComplianceRecordDTO();
        complianceRecordDTO.setComplianceId(complianceRecord.getComplianceId());
        complianceRecordDTO.setEntityType(complianceRecord.getEntityType());
        complianceRecordDTO.setNotes(complianceRecord.getNotes());
        complianceRecordDTO.setDate(complianceRecord.getDate());
        return complianceRecordDTO;
    }
}
