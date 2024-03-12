package org.omega.vktesttask.repository;

import org.omega.vktesttask.entity.AuditEntity;
import org.omega.vktesttask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<AuditEntity, Long> {
    List<AuditEntity> findByUser(User user);
    List<AuditEntity> findByUserAndDateAfter(User user, Instant date);
}
