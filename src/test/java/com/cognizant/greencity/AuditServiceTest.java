package com.cognizant.greencity; // 1. FIXED: Matches the folder structure

import com.cognizant.greencity.dto.audit.AuditCreateRequest;
import com.cognizant.greencity.dto.audit.AuditResponse;
import com.cognizant.greencity.dto.audit.AuditUpdateRequest;
import com.cognizant.greencity.entity.Audit;
import com.cognizant.greencity.entity.ComplianceRecord;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.repository.AuditRepository;
import com.cognizant.greencity.repository.ComplianceRecordRepository;
import com.cognizant.greencity.repository.UserRepository;
import com.cognizant.greencity.security.UserPrincipal;
import com.cognizant.greencity.service.AuditLogService;
import com.cognizant.greencity.service.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {

    @Mock private AuditRepository auditRepository;
    @Mock private ComplianceRecordRepository complianceRecordRepository;
    @Mock private UserRepository userRepository;
    @Mock private AuditLogService auditLogService; // 2. FIXED: Explicit type to resolve 'record' method
    @Mock private ModelMapper modelMapper;
    @Mock private Authentication authentication;

    @InjectMocks
    private AuditService auditService; // 3. FIXED: Explicit type to resolve 'create', 'update', etc.

    private User mockUser;
    private UserPrincipal principal;
    private ComplianceRecord mockCompliance;
    private Audit mockAudit;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setUserId(1);

        // 4. FIXED: Provided 5 arguments instead of 4.
        // (Id, Email, Password, Enabled-status, Authorities)
        principal = new UserPrincipal(1, "test@test.com", "password", true, Collections.emptyList());

        mockCompliance = new ComplianceRecord();
        mockCompliance.setComplianceId(100);

        mockAudit = new Audit();
        mockAudit.setAuditId(500);
        mockAudit.setComplianceRecord(mockCompliance);
        mockAudit.setOfficer(mockUser);
    }

    @Test
    @DisplayName("Create Audit - Success")
    void create_Success() {
        AuditCreateRequest request = new AuditCreateRequest();
        when(authentication.getPrincipal()).thenReturn(principal);
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        when(complianceRecordRepository.findById(100)).thenReturn(Optional.of(mockCompliance));
        when(modelMapper.map(any(), eq(Audit.class))).thenReturn(new Audit());
        when(auditRepository.save(any(Audit.class))).thenReturn(mockAudit);
        when(modelMapper.map(any(Audit.class), eq(AuditResponse.class))).thenReturn(new AuditResponse());

        AuditResponse response = auditService.create(100, request, authentication);

        assertThat(response).isNotNull();
        verify(auditLogService).record(any(), eq("AUDIT_CREATE"), anyString());
    }

    @Test
    @DisplayName("Get Audit - Success")
    void getByCompliance_Success() {
        when(auditRepository.findByAuditIdAndComplianceRecord_ComplianceId(500, 100))
                .thenReturn(Optional.of(mockAudit));
        when(modelMapper.map(any(), eq(AuditResponse.class))).thenReturn(new AuditResponse());

        AuditResponse response = auditService.getByCompliance(100, 500);

        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("Update Audit - Success")
    void update_Success() {
        AuditUpdateRequest updateRequest = new AuditUpdateRequest();
        updateRequest.setFindings("Updated Findings");

        when(authentication.getPrincipal()).thenReturn(principal);
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        when(auditRepository.findByAuditIdAndComplianceRecord_ComplianceId(500, 100))
                .thenReturn(Optional.of(mockAudit));
        when(auditRepository.save(any(Audit.class))).thenReturn(mockAudit);
        when(modelMapper.map(any(), eq(AuditResponse.class))).thenReturn(new AuditResponse());

        AuditResponse response = auditService.update(100, 500, updateRequest, authentication);

        assertThat(response).isNotNull();
        verify(auditLogService).record(any(), eq("AUDIT_UPDATE"), anyString());
    }

    @Test
    @DisplayName("Delete Audit - Success")
    void delete_Success() {
        when(authentication.getPrincipal()).thenReturn(principal);
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        when(auditRepository.findByAuditIdAndComplianceRecord_ComplianceId(500, 100))
                .thenReturn(Optional.of(mockAudit));

        auditService.delete(100, 500, authentication);

        verify(auditRepository).delete(any(Audit.class));
        verify(auditLogService).record(any(), eq("AUDIT_DELETE"), anyString());
    }
}