// LoanStatusHistory.java
package com.shieldpointcapital.lrms.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_status_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanStatusHistory {

    // ─── Primary key ─────────────────────────────────────────────

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id", nullable = false)
    private Long historyId;

    // ─── Loan reference ──────────────────────────────────────────

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tracking_id", nullable = false)
    private LoanTracking loanTracking;

    // ─── Status change ───────────────────────────────────────────

    @Column(name = "previous_status", length = 20)
    private String previousStatus;

    @Column(name = "new_status", nullable = false, length = 20)
    private String newStatus;

    @Column(name = "change_date", nullable = false, updatable = false)
    private LocalDateTime changeDate;

    @Column(name = "changed_by", nullable = false, length = 20)
    private String changedBy;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    // ─── Immutability guard ──────────────────────────────────────

    @PreUpdate
    public void onUpdate() {
        throw new UnsupportedOperationException(
            "LoanStatusHistory records are immutable — " +
            "every status change creates a new entry"
        );
    }
}