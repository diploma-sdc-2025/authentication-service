package org.java.diploma.service.authservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "user_session",
        indexes = {
                @Index(name = "idx_user_sessions_user_id", columnList = "user_id"),
                @Index(name = "idx_user_sessions_refresh_token_id", columnList = "refresh_token_id")
        }
)
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refresh_token_id")
    private RefreshToken refreshToken;

    @Column(name = "device_info")
    private String deviceInfo;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "last_activity_at", nullable = false)
    private Instant lastActivityAt;

    @Column(name = "ended_at")
    private Instant endedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        this.startedAt = now;
        this.lastActivityAt = now;
    }

    public boolean isActive() {
        return endedAt == null;
    }
}
