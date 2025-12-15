package org.java.diploma.service.authservice.repository;

import org.java.diploma.service.authservice.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSessionRepository extends JpaRepository<UserSession, Integer> {
    List<UserSession> findAllByUser_IdOrderByStartedAtDesc(Integer userId);
}
