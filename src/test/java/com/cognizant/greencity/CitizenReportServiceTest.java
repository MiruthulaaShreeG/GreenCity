package com.cognizant.greencity;

import com.cognizant.greencity.dto.report.CitizenReportCreateRequest;
import com.cognizant.greencity.dto.report.CitizenReportUpdateRequest;
import com.cognizant.greencity.entity.CitizenReport;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.exception.UnauthorizedException;
import com.cognizant.greencity.repository.CitizenReportRepository;
import com.cognizant.greencity.repository.UserRepository;
import com.cognizant.greencity.security.UserPrincipal;
import com.cognizant.greencity.service.AuditLogService;
import com.cognizant.greencity.service.CitizenReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CitizenReportServiceTest {

    private CitizenReportRepository citizenReportRepository;
    private UserRepository userRepository;
    private AuditLogService auditLogService;
    private ModelMapper modelMapper;
    private CitizenReportService service;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        citizenReportRepository = mock(CitizenReportRepository.class);
        userRepository = mock(UserRepository.class);
        auditLogService = mock(AuditLogService.class);
        modelMapper = new ModelMapper();
        service = new CitizenReportService(citizenReportRepository, userRepository, auditLogService, modelMapper);

        UserPrincipal principal = new UserPrincipal(1, "testUser", "password", null,true);
        authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(principal);
    }

    @Test
    void testCreateReport() {
        CitizenReportCreateRequest request = new CitizenReportCreateRequest();
        request.setType(CitizenReport.ReportType.valueOf("WASTE"));
        request.setLocation("Main Street");

        User user = new User();
        user.setUserId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        CitizenReport saved = new CitizenReport();
        saved.setReportId(100);
        saved.setCitizen(user);
        when(citizenReportRepository.save(any(CitizenReport.class))).thenReturn(saved);

        var response = service.create(request, authentication);

        assertNotNull(response);
        assertEquals(1, response.getCitizenId());
        verify(auditLogService).record(user, "CITIZEN_REPORT_CREATE", "citizen-reports/100");
    }

    @Test
    void testUpdateUnauthorized() {
        CitizenReport report = new CitizenReport();
        User otherUser = new User();
        otherUser.setUserId(2);
        report.setCitizen(otherUser);
        when(citizenReportRepository.findById(100)).thenReturn(Optional.of(report));

        CitizenReportUpdateRequest request = new CitizenReportUpdateRequest();
        request.setStatus("CLOSED");

        assertThrows(UnauthorizedException.class, () -> service.update(100, request, authentication));
    }

    @Test
    void testGetEntityNotFound() {
        when(citizenReportRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.getMine(999, authentication));
    }
}
