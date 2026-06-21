package com.shieldpointcapital.lrms.domain.entity;

import com.shieldpointcapital.lrms.domain.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanAccount {

    // ─── Primary identification ──────────────────────────────────

    @Id
    @Column(name = "loan_account_id", nullable = false, length = 20)
    private String loanAccountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_account_id", nullable = false)
    private CompanyAccount masterAccount;

    @Column(name = "account_name", nullable = false, length = 100)
    private String accountName;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false, length = 20)
    private AccountStatus accountStatus;

    // ─── Officer assignment ──────────────────────────────────────

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_officer_id")
    private StaffAccount assignedOfficer;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    @Column(name = "previously_assigned_to", length = 20)
    private String previouslyAssignedTo;

    // ─── Capital tracking ────────────────────────────────────────

    @Column(name = "allocated_capital",
            nullable = false,
            precision = 15, scale = 2)
    private BigDecimal allocatedCapital;

    @Column(name = "current_capital",
            nullable = false,
            precision = 15, scale = 2)
    private BigDecimal currentCapital;

    @Column(name = "capital_deployed",
            nullable = false,
            precision = 15, scale = 2)
    private BigDecimal capitalDeployed;

    // ─── Loan counts ─────────────────────────────────────────────

    @Column(name = "total_loans_disbursed", nullable = false)
    private Integer totalLoansDisbursed;

    @Column(name = "active_loans_count", nullable = false)
    private Integer activeLoansCount;

    @Column(name = "overdue_loans_count", nullable = false)
    private Integer overdueLoansCount;

    @Column(name = "defaulted_loans_count", nullable = false)
    private Integer defaultedLoansCount;

    @Column(name = "fully_paid_loans_count", nullable = false)
    private Integer fullyPaidLoansCount;

    // ─── Revenue tracking ────────────────────────────────────────

    @Column(name = "projected_revenue",
            nullable = false,
            precision = 15, scale = 2)
    private BigDecimal projectedRevenue;

    @Column(name = "realised_revenue",
            nullable = false,
            precision = 15, scale = 2)
    private BigDecimal realisedRevenue;

    @Column(name = "revenue_at_risk",
            nullable = false,
            precision = 15, scale = 2)
    private BigDecimal revenueAtRisk;

    @Column(name = "total_losses",
            nullable = false,
            precision = 15, scale = 2)
    private BigDecimal totalLosses;

    @Column(name = "total_interest_foregone",
            nullable = false,
            precision = 15, scale = 2)
    private BigDecimal totalInterestForegone;

    // ─── Performance metrics ─────────────────────────────────────

    @Column(name = "default_rate",
            nullable = false,
            precision = 5, scale = 2)
    private BigDecimal defaultRate;

    @Column(name = "collection_rate",
            nullable = false,
            precision = 5, scale = 2)
    private BigDecimal collectionRate;

    // ─── Audit ───────────────────────────────────────────────────

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false, length = 20,
            updatable = false)
    private String createdBy;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;
}