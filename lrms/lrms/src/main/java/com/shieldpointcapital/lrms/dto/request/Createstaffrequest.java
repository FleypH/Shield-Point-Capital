package com.shieldpointcapital.lrms.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.shieldpointcapital.lrms.domain.enums.Role;

/**
 * Payload for ADMIN creating a new staff account.
 * password is plain text here — hash with BCrypt in the service layer,
 * never persist this record directly.
 */
public record CreateStaffrequest(
    @NotBlank(message = "national ID required")
    @Pattern(regexp = "^\\d{2}-\\d{6,7}[A-Z]\\d{2}$", message = "National ID must match Zimbabwe format e.g. 63-123456N71")
    String nationalId,

    @NotBlank(message = "first name required")
    @Size(max = 100)
    String firstName,

    @NotBlank(message = "last name required")
    @Size(max = 100)
    String lastName,
    
    @NotBlank(message = "date of birth required")
    @Past(message = "date of birth must be in the past")
    LocalDate dateOfBirth,

    @NotBlank(message = "address required")
    @Size(max = 255)
    String address,

    @NotBlank(message = "email required")
    @Email(message = "Email must be a valid email address")
    String email,

    @NotBlank(message = "phone number required")
    @Pattern(regexp =" ^0\\d{9}$", message = "Phone number must be a valid Zimbabwean number e.g. 0771000001")
    String phoneNumber,
    
    @NotBlank(message = "job title required")
    @Size(max = 100)
    String jobTitle,

    @NotNull(message = "role required")
    Role role,

    String loanAccountId,

    @NotBlank(message = "password required")
    @Size(min = 8, message = "password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).+$", 
    message = "Password must contain upper, lower, digit and special character")
    
    String password


){}
