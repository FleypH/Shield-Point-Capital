// CompanyLedger.java
package com.shieldpointcapital.lrms.domain.entity;

import com.shieldpointcapital.lrms.domain.enums.EventType;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "company_ledger")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyLedger {

    // ─── Primary key ─────────────────────────────────────────────

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ledger_id", nullable = false)
    private Long ledgerId;

    // ─── References ──────────────────────────────────────────────

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_account_id", nullable = false)
    private LoanAccount loanAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tracking_id", nullable = false)
    private LoanTracking loanTracking;

    // ─── Event details ───────────────────────────────────────────

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 30)
    private EventType eventType;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @Column(name = "recorded_by", nullable = false, length = 20)
    private String recordedBy;

    @Column(name = "recorded_at", nullable = false, updatable = false)
    private LocalDateTime recordedAt;

    // ─── Financial components ────────────────────────────────────

    @Column(name = "capital_effect",
            nullable = false, precision = 15, scale = 2)
    private BigDecimal capitalEffect;

    @Column(name = "principal_component",
            nullable = false, precision = 15, scale = 2)
    private BigDecimal principalComponent;

    @Column(name = "interest_component",
            nullable = false, precision = 15, scale = 2)
    private BigDecimal interestComponent;

    @Column(name = "penalty_component",
            nullable = false, precision = 15, scale = 2)
    private BigDecimal penaltyComponent;

    @Column(name = "loss_amount",
            nullable = false, precision = 15, scale = 2)
    private BigDecimal lossAmount;

    @Column(name = "revenue_effect",
            nullable = false, precision = 15, scale = 2)
    private BigDecimal revenueEffect;

    // ─── Balance snapshots ───────────────────────────────────────

    @Column(name = "sub_account_balance_after",
            nullable = false, precision = 15, scale = 2)
    private BigDecimal subAccountBalanceAfter;

    @Column(name = "master_balance_after",
            nullable = false, precision = 15, scale = 2)
    private BigDecimal masterBalanceAfter;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    // ─── Immutability guard ──────────────────────────────────────

    @PreUpdate
    public void onUpdate() {
        throw new UnsupportedOperationException(
            "CompanyLedger records are immutable — " +
            "every financial event creates a new entry"
        );
    }
}