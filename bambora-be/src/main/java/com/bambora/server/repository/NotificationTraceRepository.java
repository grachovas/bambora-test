package com.bambora.server.repository;

import com.bambora.server.domain.NotificationTrace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTraceRepository extends JpaRepository<NotificationTrace, Long> {
}
