// ConflictLog.java
package com.shieldpointcapital.lrms.domain.entity;

import com.shieldpointcapital.lrms.domain.enums.ConflictResolution;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conflict_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConflictLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conflict_id", nullable = false)
    private Long conflictId;

    @Column(name = "device_id", nullable = false, length = 100)
    private String deviceId;

    @Column(name = "staff_id", nullable = false, length = 20)
    private String staffId;

    @Column(name = "table_name", nullable = false, length = 50)
    private String tableName;

    @Column(name = "record_id", nullable = false, length = 50)
    private String recordId;

    @Column(name = "client_value",
            nullable = false, columnDefinition = "TEXT")
    private String clientValue;

    @Column(name = "server_value",
            nullable = false, columnDefinition = "TEXT")
    private String serverValue;

    @Column(name = "conflict_detected_at",
            nullable = false, updatable = false)
    private LocalDateTime conflictDetectedAt;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    @Column(name = "resolved_by", length = 20)
    private String resolvedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "resolution", length = 20)
    private ConflictResolution resolution;
}