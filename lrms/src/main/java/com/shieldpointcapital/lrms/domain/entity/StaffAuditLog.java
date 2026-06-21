// StaffAuditLog.java
package com.shieldpointcapital.lrms.domain.entity;

import com.shieldpointcapital.lrms.domain.enums.ActionType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "staff_audit_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffAuditLog {

    // ─── Primary key ─────────────────────────────────────────────

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", nullable = false)
    private Long logId;

    // ─── Staff reference ─────────────────────────────────────────

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = false)
    private StaffAccount staffAccount;

    // ─── Action details ──────────────────────────────────────────

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false, length = 30)
    private ActionType actionType;

    @Column(name = "target_table", length = 50)
    private String targetTable;

    @Column(name = "target_id", length = 50)
    private String targetId;

    @Column(name = "action_timestamp", nullable = false, updatable = false)
    private LocalDateTime actionTimestamp;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    // ─── Before and after snapshots ──────────────────────────────

    @Column(name = "previous_value", columnDefinition = "TEXT")
    private String previousValue;

    @Column(name = "new_value", columnDefinition = "TEXT")
    private String newValue;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    // ─── Immutability guard ──────────────────────────────────────

    @PreUpdate
    public void onUpdate() {
        throw new UnsupportedOperationException(
            "StaffAuditLog records are immutable — " +
            "audit logs can never be modified"
        );
    }
}