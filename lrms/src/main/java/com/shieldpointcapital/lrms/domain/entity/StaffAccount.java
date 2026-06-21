package com.shieldpointcapital.lrms.domain.entity;

import com.shieldpointcapital.lrms.domain.converter.EncryptedStringConverter;
import com.shieldpointcapital.lrms.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "staff_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffAccount {

    // ─── Primary identification ──────────────────────────────────

    @Id
    @Column(name = "staff_id", nullable = false, length = 20)
    private String staffId;

    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "national_id", unique = true, length = 20)
    private String nationalId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "address")
    private String address;

    // ─── Contact & login ─────────────────────────────────────────

    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;

    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "phone_number", unique = true, nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "job_title", nullable = false, length = 50)
    private String jobTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Role role;

    // ─── Assignment ──────────────────────────────────────────────

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_account_id")
    private LoanAccount loanAccount;

    // ─── Security ────────────────────────────────────────────────

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "password_reset_token", length = 100)
    private String passwordResetToken;

    @Column(name = "token_expires_at")
    private LocalDateTime tokenExpiresAt;

    @Column(name = "token_version", nullable = false)
    private Integer tokenVersion;

    // ─── Account status ──────────────────────────────────────────

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "failed_login_count", nullable = false)
    private Byte failedLoginCount;

    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;

    // ─── Activity tracking ───────────────────────────────────────

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "last_login_ip", length = 45)
    private String lastLoginIp;

    // ─── Audit ───────────────────────────────────────────────────

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;
}