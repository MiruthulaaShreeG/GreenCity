package com.cognizant.greencity.config;

import com.cognizant.greencity.dto.audit.AuditLogResponse;
import com.cognizant.greencity.dto.audit.AuditResponse;
import com.cognizant.greencity.dto.feedback.FeedbackResponse;
import com.cognizant.greencity.dto.project.ImpactResponse;
import com.cognizant.greencity.dto.project.MilestoneResponse;
import com.cognizant.greencity.dto.project.ProjectResponse;
import com.cognizant.greencity.dto.report.CitizenReportResponse;
import com.cognizant.greencity.dto.resource.ResourceResponse;
import com.cognizant.greencity.dto.resourceusage.ResourceUsageResponse;
import com.cognizant.greencity.entity.Audit;
import com.cognizant.greencity.entity.AuditLog;
import com.cognizant.greencity.entity.CitizenReport;
import com.cognizant.greencity.entity.Feedback;
import com.cognizant.greencity.entity.Impact;
import com.cognizant.greencity.entity.Milestone;
import com.cognizant.greencity.entity.Project;
import com.cognizant.greencity.entity.Resource;
import com.cognizant.greencity.entity.ResourceUsage;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        // These ID fields are populated manually in services from nested entities.
        modelMapper.typeMap(Project.class, ProjectResponse.class)
                .addMappings(mapper -> mapper.skip(ProjectResponse::setCreatedBy));
        modelMapper.typeMap(Resource.class, ResourceResponse.class)
                .addMappings(mapper -> mapper.skip(ResourceResponse::setProjectId));
        modelMapper.typeMap(ResourceUsage.class, ResourceUsageResponse.class)
                .addMappings(mapper -> mapper.skip(ResourceUsageResponse::setResourceId));
        modelMapper.typeMap(CitizenReport.class, CitizenReportResponse.class)
                .addMappings(mapper -> mapper.skip(CitizenReportResponse::setCitizenId));
        modelMapper.typeMap(Feedback.class, FeedbackResponse.class)
                .addMappings(mapper -> mapper.skip(FeedbackResponse::setCitizenId));
        modelMapper.typeMap(Impact.class, ImpactResponse.class)
                .addMappings(mapper -> mapper.skip(ImpactResponse::setProjectId));
        modelMapper.typeMap(Milestone.class, MilestoneResponse.class)
                .addMappings(mapper -> mapper.skip(MilestoneResponse::setProjectId));
        modelMapper.typeMap(Audit.class, AuditResponse.class)
                .addMappings(mapper -> {
                    mapper.skip(AuditResponse::setComplianceId);
                    mapper.skip(AuditResponse::setOfficerId);
                });
        modelMapper.typeMap(AuditLog.class, AuditLogResponse.class)
                .addMappings(mapper -> mapper.skip(AuditLogResponse::setUserId));

        return modelMapper;
    }
}

