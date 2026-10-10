// PaymentLog.java
package com.shieldpointcapital.lrms.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentLog {

    // ─── Primary key ─────────────────────────────────────────────

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    // ─── Loan reference ──────────────────────────────────────────

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tracking_id", nullable = false)
    private LoanTracking loanTracking;

    // ─── Payment details ─────────────────────────────────────────

    @Column(name = "amount_received",
            nullable = false, precision = 15, scale = 2)
    private BigDecimal amountReceived;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "payment_method", nullable = false, length = 30)
    private String paymentMethod;

    @Column(name = "payment_reference", length = 100)
    private String paymentReference;

    // ─── Audit ───────────────────────────────────────────────────

    @Column(name = "recorded_by", nullable = false, length = 20)
    private String recordedBy;

    @Column(name = "recorded_at", nullable = false, updatable = false)
    private LocalDateTime recordedAt;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    // ─── Immutability guard ──────────────────────────────────────

    @PreUpdate
    public void onUpdate() {
        throw new UnsupportedOperationException(
            "PaymentLog records are immutable — " +
            "corrections must be recorded as a new entry"
        );
    }
}