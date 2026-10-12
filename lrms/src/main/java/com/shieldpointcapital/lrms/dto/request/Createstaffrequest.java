package com.shieldpointcapital.lrms.dto.request;

import java.time.LocalDate;

import com.shieldpointcapital.lrms.domain.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Payload for ADMIN creating a new staff account.
 * password is plain text here — hash with BCrypt in the service layer,
 * never persist this record directly.
 */
public record CreateStaffRequest(

        @NotBlank(message = "National ID is required")
        @Pattern(regexp = "^\\d{2}-\\d{6,7}[A-Z]\\d{2}$",
                 message = "National ID must match Zimbabwe format e.g. 63-123456N71")
        String nationalId,

        @NotBlank(message = "First name is required")
        @Size(max = 100)
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 100)
        String lastName,

        // @NotNull, not @NotBlank: @NotBlank only works on Strings
        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,

        @NotBlank(message = "Address is required")
        @Size(max = 255)
        String address,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be a valid email address")
        String email,

        // No space before the ^, or no phone number can ever match
        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^0\\d{9}$",
                 message = "Phone number must be a valid Zimbabwean number e.g. 0771000001")
        String phoneNumber,

        @NotBlank(message = "Job title is required")
        @Size(max = 100)
        String jobTitle,

        @NotNull(message = "Role is required")
        Role role,

        // Nullable: only LOAN_OFFICER needs one. Enforce that in the service.
        String loanAccountId,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).+$",
                 message = "Password must contain upper, lower, digit and special character")
        String password

) {}