package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.compliance.ComplianceRecordCreateRequest;
import com.cognizant.greencity.dto.compliance.ComplianceRecordResponse;
import com.cognizant.greencity.dto.compliance.ComplianceRecordUpdateRequest;
import com.cognizant.greencity.entity.Audit;
import com.cognizant.greencity.entity.ComplianceRecord;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.BadRequestException;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.repository.AuditRepository;
import com.cognizant.greencity.repository.ComplianceRecordRepository;
import com.cognizant.greencity.repository.UserRepository;
import com.cognizant.greencity.security.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComplianceRecordServiceTest {

    @Mock
    private ComplianceRecordRepository complianceRecordRepository;
    @Mock
    private AuditRepository auditRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuditLogService auditLogService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private ComplianceRecordService complianceRecordService;

    private ComplianceRecord record;
    private User user;
    private UserPrincipal principal;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1);

        principal = mock(UserPrincipal.class);

        record = new ComplianceRecord();
        record.setComplianceId(101);
        record.setResult("PASS");
    }

    @Test
    void list_ShouldReturnResponseList() {
        when(complianceRecordRepository.findAll()).thenReturn(List.of(record));
        when(modelMapper.map(any(), eq(ComplianceRecordResponse.class))).thenReturn(new ComplianceRecordResponse());

        List<ComplianceRecordResponse> result = complianceRecordService.list();

        assertFalse(result.isEmpty());
        verify(complianceRecordRepository).findAll();
    }

    @Test
    void get_WhenExists_ShouldReturnResponse() {
        when(complianceRecordRepository.findById(101)).thenReturn(Optional.of(record));
        when(modelMapper.map(record, ComplianceRecordResponse.class)).thenReturn(new ComplianceRecordResponse());

        ComplianceRecordResponse result = complianceRecordService.get(101);

        assertNotNull(result);
    }

    @Test
    void get_WhenNotExists_ShouldThrowNotFound() {
        when(complianceRecordRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> complianceRecordService.get(999));
    }

    @Test
    void create_ShouldSaveAndLog() {
        // Setup Security Context Mocking
        mockAuthentication();

        ComplianceRecordCreateRequest request = new ComplianceRecordCreateRequest();
        request.setResult("PASS");
        request.setEntityId(1);

        when(complianceRecordRepository.save(any(ComplianceRecord.class))).thenReturn(record);
        when(modelMapper.map(any(), eq(ComplianceRecordResponse.class))).thenReturn(new ComplianceRecordResponse());

        complianceRecordService.create(request, authentication);

        verify(complianceRecordRepository).save(any(ComplianceRecord.class));
        verify(auditLogService).record(eq(user), eq("COMPLIANCE_CREATE"), anyString());
    }

    @Test
    void update_ShouldModifyFields() {
        mockAuthentication();
        ComplianceRecordUpdateRequest updateRequest = new ComplianceRecordUpdateRequest();
        updateRequest.setResult("FAIL");

        when(complianceRecordRepository.findById(101)).thenReturn(Optional.of(record));
        when(complianceRecordRepository.save(any(ComplianceRecord.class))).thenReturn(record);

        complianceRecordService.update(101, updateRequest, authentication);

        assertEquals("FAIL", record.getResult());
        verify(auditLogService).record(any(), eq("COMPLIANCE_UPDATE"), anyString());
    }

    @Test
    void delete_WhenNoAudits_ShouldDelete() {
        mockAuthentication();
        when(complianceRecordRepository.findById(101)).thenReturn(Optional.of(record));
        when(auditRepository.findByComplianceRecord_ComplianceId(101)).thenReturn(Collections.emptyList());

        complianceRecordService.delete(101, authentication);

        verify(complianceRecordRepository).delete(record);
        verify(auditLogService).record(any(), eq("COMPLIANCE_DELETE"), anyString());
    }

    @Test
    void delete_WhenAuditsExist_ShouldThrowBadRequest() {
        mockAuthentication();
        when(complianceRecordRepository.findById(101)).thenReturn(Optional.of(record));
        when(auditRepository.findByComplianceRecord_ComplianceId(101)).thenReturn(List.of(new Audit()));

        assertThrows(BadRequestException.class, () -> complianceRecordService.delete(101, authentication));
        verify(complianceRecordRepository, never()).delete(any());
    }

    /**
     * Helper to mock the authentication flow used in currentUser()
     */
    private void mockAuthentication() {
        when(authentication.getPrincipal()).thenReturn(principal);
        when(principal.getUserId()).thenReturn(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
    }
}