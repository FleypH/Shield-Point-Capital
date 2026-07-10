package com.shieldpointcapital.lrms.dto.response;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.shieldpointcapital.lrms.domain.enums.Role;
/**
 * Outbound view of a staff account. Deliberately excludes: passwordHash,
 * tokenVersion, failedLoginCount — none of these should ever leave the
 * server. nationalId/phone/address arrive here already decrypted by
 * EncryptedStringConverter on entity load.
 */
public record Staffresponse(
    String staffId,
    String nationalId,
    String firstName,
    String lastName,
    LocalDate dateOfBirth,
    String address,
    String email,
    String phoneNumber,
    String jobTitle,
    Role role,
    String loanAccountId,
    boolean isActive,
    boolean isLocked,
    LocalDateTime createdAt,
    String createdBy
)
{}
