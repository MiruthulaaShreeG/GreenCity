package com.cognizant.greencity;

import com.cognizant.greencity.dto.resource.ResourceResponse;
import com.cognizant.greencity.entity.Resource;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.repository.ProjectRepository;
import com.cognizant.greencity.repository.ResourceRepository;
import com.cognizant.greencity.repository.UserRepository;
import com.cognizant.greencity.service.AuditLogService;
import com.cognizant.greencity.service.ResourceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResourceServiceTest {

    @Mock
    private ResourceRepository resourceRepository;
    @Mock private ProjectRepository projectRepository;
    @Mock private UserRepository userRepository;
    @Mock private AuditLogService auditLogService;
    @Mock private ModelMapper modelMapper;

    @InjectMocks
    private ResourceService resourceService;

    @Test
    void testGetResource_success() {
        Resource resource = new Resource();
        resource.setResourceId(1);
        resource.setType("Water");

        when(resourceRepository.findById(1)).thenReturn(Optional.of(resource));
        when(modelMapper.map(resource, ResourceResponse.class))
                .thenReturn(new ResourceResponse(1, 10, "Water", "City Center", 100.0, "ACTIVE"));

        ResourceResponse response = resourceService.get(1);
        assertEquals("Water", response.getType());
    }

    @Test
    void testGetResource_notFound() {
        when(resourceRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> resourceService.get(99));
    }
}
