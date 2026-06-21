package com.shieldpointcapital.lrms.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "company_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyAccount {
    @Id
    @Column( name = "account_id" )
    private Byte accountID;

    @Column(name = "company_name",
        nullable = false, 
        length = 100)
    private String companyName;

     // ─── Capital tracking ───────────────────────────────────────

    @Column(name = "total_opening_capital",
        nullable = false, 
        precision = 15, scale = 2)
    private BigDecimal totalOpeningCapital;

    @Column(name = "total_allocated_capital", 
        nullable = false,
        precision = 15, scale = 2)
    private BigDecimal totalAllocatedCapital;

    @Column(name = "unallocated_capital",
        nullable = false, 
        precision = 15, scale = 2)
    private BigDecimal unallocatedCapital;

    @Column (name = "current_capital" ,
        nullable = false, 
        precision = 15, scale = 2)
    private BigDecimal currentCapital;

    @Column(name = "capital_deployed",
    nullable = false,
    precision = 15, scale = 2)
    private BigDecimal capitalDeployed;

    // ─── Loan counts ────────────────────────────────────────────

    @Column(name = "total_loans_disbursed", nullable = false)
    private Integer totalLoansDisbursed;

    @Column(name = "total_disbursed_amount",
        nullable = false,
        precision = 15, scale = 2)
    private BigDecimal totalDisbursedAmount;

    @Column(name = "active_loans_count", nullable = false)
    private Integer activeLoansCount;

    @Column(name = "overdue_loans_count", nullable = false)
    private Integer overdueLoansCount;

    @Column(name = "defaulted_loans_count", nullable = false)
    private Integer defaultedLoansCount;

    @Column(name = "fully_paid_loans_count", nullable = false)
    private Integer fullyPaidLoansCount;

    // ─── Revenue tracking ───────────────────────────────────────

    @Column(name = "projected_revenue",
        nullable = false,
        precision = 15, scale = 2)
    private BigDecimal projectedRevenue;

    @Column(name = "projected_revenue_overdue",
        nullable = false,
        precision = 15, scale = 2)
    private BigDecimal projectedRevenueOverdue;

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

    // ─── Performance metrics ────────────────────────────────────

    @Column(name = "collection_rate",
        nullable = false,
        precision = 5, scale = 2)
    private BigDecimal collectionRate;

    @Column(name = "default_rate",
        nullable = false,
        precision = 5, scale = 2)
    private BigDecimal defaultRate;

    @Column(name = "capital_utilisation_rate",
        nullable = false,
        precision = 5, scale = 2)
    private BigDecimal capitalUtilisationRate;

    // ─── Audit ──────────────────────────────────────────────────

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;
}





