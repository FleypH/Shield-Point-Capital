// SyncRecord.java
package com.shieldpointcapital.lrms.domain.entity;

import com.shieldpointcapital.lrms.domain.enums.SyncState;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sync_record")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SyncRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sync_id", nullable = false)
    private Long syncId;

    @Column(name = "device_id", nullable = false, length = 100)
    private String deviceId;

    @Column(name = "staff_id", nullable = false, length = 20)
    private String staffId;

    @Column(name = "sync_started_at", nullable = false, updatable = false)
    private LocalDateTime syncStartedAt;

    @Column(name = "sync_completed_at")
    private LocalDateTime syncCompletedAt;

    @Column(name = "records_pushed", nullable = false)
    private Integer recordsPushed;

    @Column(name = "records_pulled", nullable = false)
    private Integer recordsPulled;

    @Enumerated(EnumType.STRING)
    @Column(name = "sync_status", nullable = false, length = 20)
    private SyncState syncStatus;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;
}