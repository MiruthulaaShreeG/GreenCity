package com.cognizant.greencity;

import com.cognizant.greencity.dto.resourceusage.ResourceUsageCreateRequest;
import com.cognizant.greencity.dto.resourceusage.ResourceUsageResponse;
import com.cognizant.greencity.dto.resourceusage.ResourceUsageUpdateRequest;
import com.cognizant.greencity.entity.Resource;
import com.cognizant.greencity.entity.ResourceUsage;
import com.cognizant.greencity.repository.ResourceRepository;
import com.cognizant.greencity.repository.ResourceUsageRepository;
import com.cognizant.greencity.repository.UserRepository;
import com.cognizant.greencity.service.AuditLogService;
import com.cognizant.greencity.service.ResourceUsageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResourceUsageServiceTest {

    @Mock
    private ResourceUsageRepository usageRepository;
    @Mock private ResourceRepository resourceRepository;
    @Mock private UserRepository userRepository;
    @Mock private AuditLogService auditLogService;
    @Mock private ModelMapper modelMapper;

    @InjectMocks
    private ResourceUsageService resourceUsageService;

    @Test
    void testCreateUsage_success() {
        Resource resource = new Resource();
        resource.setResourceId(10);

        ResourceUsage usage = new ResourceUsage();
        usage.setUsageId(1);
        usage.setResource(resource);
        usage.setQuantity(30.0);

        when(resourceRepository.findById(10)).thenReturn(Optional.of(resource));
        when(usageRepository.save(any(ResourceUsage.class))).thenReturn(usage);
        when(modelMapper.map(usage, ResourceUsageResponse.class))
                .thenReturn(new ResourceUsageResponse(1, 10, 30.0, LocalDateTime.now(), "RECORDED"));

        ResourceUsageCreateRequest request = new ResourceUsageCreateRequest(30.0, "RECORDED");
        ResourceUsageResponse response = resourceUsageService.create(10, request, null);

        assertEquals(30.0, response.getQuantity());
        assertEquals(10, response.getResourceId());
    }

    @Test
    void testUpdateUsage_success() {
        ResourceUsage usage = new ResourceUsage();
        usage.setUsageId(1);
        usage.setQuantity(20.0);

        when(usageRepository.findById(1)).thenReturn(Optional.of(usage));
        when(usageRepository.save(usage)).thenReturn(usage);
        when(modelMapper.map(usage, ResourceUsageResponse.class))
                .thenReturn(new ResourceUsageResponse(1, 10, 40.0, LocalDateTime.now(), "UPDATED"));

        ResourceUsageUpdateRequest request = new ResourceUsageUpdateRequest(40.0, "UPDATED");
        ResourceUsageResponse response = resourceUsageService.update(1, request, null);

        assertEquals(40.0, response.getQuantity());
        assertEquals("UPDATED", response.getStatus());
    }

    @Test
    void testDeleteUsage_success() {
        ResourceUsage usage = new ResourceUsage();
        usage.setUsageId(1);

        when(usageRepository.findById(1)).thenReturn(Optional.of(usage));
        resourceUsageService.delete(1, null);

        verify(usageRepository).delete(usage);
    }
}
