package com.cognizant.greencity.dao;
import com.cognizant.greencity.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog,Integer>{

}
