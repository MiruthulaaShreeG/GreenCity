package com.cognizant.greencity.dao;
import com.cognizant.greencity.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AuditLogRepository extends JpaRepository<Feedback,Integer> {
}
