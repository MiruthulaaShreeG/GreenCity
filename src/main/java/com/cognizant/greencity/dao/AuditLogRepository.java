package com.cognizant.greencity.dao;
import com.cognizant.greencity.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cognizant.greencity.entity.User;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog,Integer>{
    List<AuditLog> findByUser(User user);
    List<AuditLog> findByUser_UserId(Integer userId);
    List<AuditLog> findAllByOrderByTimestampDesc();
}
