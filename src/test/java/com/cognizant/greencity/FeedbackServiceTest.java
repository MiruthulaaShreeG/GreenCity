package com.cognizant.greencity;

import com.cognizant.greencity.dto.feedback.FeedbackCreateRequest;
import com.cognizant.greencity.dto.feedback.FeedbackUpdateRequest;
import com.cognizant.greencity.entity.Feedback;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.exception.UnauthorizedException;
import com.cognizant.greencity.repository.FeedbackRepository;
import com.cognizant.greencity.repository.UserRepository;
import com.cognizant.greencity.security.UserPrincipal;
import com.cognizant.greencity.service.AuditLogService;
import com.cognizant.greencity.service.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeedbackServiceTest {

    private FeedbackRepository feedbackRepository;
    private UserRepository userRepository;
    private AuditLogService auditLogService;
    private ModelMapper modelMapper;
    private FeedbackService service;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        feedbackRepository = mock(FeedbackRepository.class);
        userRepository = mock(UserRepository.class);
        auditLogService = mock(AuditLogService.class);
        modelMapper = new ModelMapper();
        service = new FeedbackService(feedbackRepository, userRepository, auditLogService, modelMapper);

        UserPrincipal principal = new UserPrincipal(1, "testUser", "password", null,true);
        authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(principal);
    }

    @Test
    void testCreateFeedback() {
        FeedbackCreateRequest request = new FeedbackCreateRequest();
        request.setCategory(Feedback.Category.valueOf("Waste"));
        request.setComments("Needs improvement");

        User user = new User();
        user.setUserId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        Feedback saved = new Feedback();
        saved.setFeedbackId(200);
        saved.setCitizen(user);
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(saved);

        var response = service.create(request, authentication);

        assertNotNull(response);
        assertEquals(1, response.getCitizenId());
        verify(auditLogService).record(user, "FEEDBACK_CREATE", "feedback/200");
    }

    @Test
    void testUpdateUnauthorized() {
        Feedback feedback = new Feedback();
        User otherUser = new User();
        otherUser.setUserId(2);
        feedback.setCitizen(otherUser);
        when(feedbackRepository.findById(200)).thenReturn(Optional.of(feedback));

        FeedbackUpdateRequest request = new FeedbackUpdateRequest();
        request.setStatus("CLOSED");

        assertThrows(UnauthorizedException.class, () -> service.update(200, request, authentication));
    }

    @Test
    void testGetEntityNotFound() {
        when(feedbackRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.getMine(999, authentication));
    }
}
