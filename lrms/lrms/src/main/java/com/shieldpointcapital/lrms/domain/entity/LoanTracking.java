// LoanTracking.java
package com.shieldpointcapital.lrms.domain.entity;

import com.shieldpointcapital.lrms.domain.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_tracking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanTracking {

    // ─── Primary key ─────────────────────────────────────────────

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tracking_id", nullable = false)
    private Long trackingId;

    // ─── Borrower and account links ──────────────────────────────

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_number", nullable = false)
    private UserInfo borrower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_account_id", nullable = false)
    private LoanAccount loanAccount;

    // ─── Loan details at disbursement ────────────────────────────

    @Column(name = "loan_type", nullable = false, length = 50)
    private String loanType;

    @Column(name = "disbursement_date", nullable = false)
    private LocalDate disbursementDate;

    @Column(name = "disbursement_amount",
            nullable = false, precision = 15, scale = 2)
    private BigDecimal disbursementAmount;

    @Column(name = "maturity_date", nullable = false)
    private LocalDate maturityDate;

    @Column(name = "total_repayment_amount",
            nullable = false, precision = 15, scale = 2)
    private BigDecimal totalRepaymentAmount;

    // ─── Repayment tracking ──────────────────────────────────────

    @Column(name = "outstanding_balance",
            nullable = false, precision = 15, scale = 2)
    private BigDecimal outstandingBalance;

    @Column(name = "amount_paid",
            nullable = false, precision = 15, scale = 2)
    private BigDecimal amountPaid;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "payment_method", length = 30)
    private String paymentMethod;

    @Column(name = "payment_reference", length = 100)
    private String paymentReference;

    // ─── Loan state ──────────────────────────────────────────────

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_status", nullable = false, length = 20)
    private LoanStatus loanStatus;

    @Column(name = "days_past_due", nullable = false)
    private Integer daysPastDue;

    @Column(name = "penalty_accrued",
            nullable = false, precision = 15, scale = 2)
    private BigDecimal penaltyAccrued;

    @Column(name = "total_amount_due",
            nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmountDue;

    // ─── Audit ───────────────────────────────────────────────────

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @Column(name = "updated_by", nullable = false, length = 20)
    private String updatedBy;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;
}