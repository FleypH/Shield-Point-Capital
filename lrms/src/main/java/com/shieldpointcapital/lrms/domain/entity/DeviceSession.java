// DeviceSession.java
package com.shieldpointcapital.lrms.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "device_session")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    @Column(name = "device_id", nullable = false, length = 100)
    private String deviceId;

    @Column(name = "staff_id", nullable = false, length = 20)
    private String staffId;

    @Column(name = "device_name", length = 100)
    private String deviceName;

    @Column(name = "last_seen_at", nullable = false)
    private LocalDateTime lastSeenAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "registered_at", nullable = false, updatable = false)
    private LocalDateTime registeredAt;
}