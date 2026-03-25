package com.cognizant.greencity.service;

import com.cognizant.greencity.entity.AuditLog;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.repository.AuditLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditLogServiceTests {

    @Mock
    private AuditLogRepository auditLogRepository;

    @InjectMocks
    private AuditLogService auditLogService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1);
        user.setName("Test User");
    }

    @Test
    @DisplayName("Should save audit log when user is provided")
    void record_Success() {

        String action = "USER_LOGIN";
        String resource = "auth/login";


        auditLogService.record(user, action, resource);


        ArgumentCaptor<AuditLog> logCaptor = ArgumentCaptor.forClass(AuditLog.class);
        verify(auditLogRepository, times(1)).save(logCaptor.capture());

        AuditLog savedLog = logCaptor.getValue();
        assertEquals(user, savedLog.getUser());
        assertEquals(action, savedLog.getAction());
        assertEquals(resource, savedLog.getResources());
        assertNotNull(savedLog.getTimestamp());
    }

    @Test
    @DisplayName("Should not save audit log when user is null")
    void record_NullUser_DoesNothing() {

        auditLogService.record(null, "ACTION", "RESOURCE");


        verify(auditLogRepository, never()).save(any(AuditLog.class));
    }
}