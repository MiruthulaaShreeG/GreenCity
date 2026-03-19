package com.cognizant.greencity.service.impl;

import com.cognizant.greencity.dao.ComplianceRecordRepository;
import com.cognizant.greencity.dto.ComplianceRecordDTO;
import com.cognizant.greencity.entity.ComplianceRecord;
import com.cognizant.greencity.service.ComplicanceRecordSercive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<ComplianceRecordDTO> getAllComplianceRecords(){
        List<ComplianceRecord> complianceRecords = complianceRecordRepository.findAll();
        return complianceRecords.stream()
                .map(this::mapTODTO)
                .toList();
    }
    public  ComplianceRecordDTO getComplianceRecordById(int id){
        ComplianceRecordDTO record = complianceRecordRepository.findById(id)
                .map(this::mapTODTO)
                .orElse(null);
        return record;
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
