package com.shieldpointcapital.lrms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Two use cases, one shape:
 *  - Self-service (PUT /staff/me/password): currentPassword is required,
 *    validate it against the caller's own hash in the service.
 *  - Admin-forced reset (PUT /staff/{staffId}/password): currentPassword
 *    is null, caller must have ADMIN role — check in the controller/service,
 *    not here.
 * Either path must increment token_version to invalidate existing sessions.
 */

public record PasswordResetRequest(

    //Null when an ADMIN is forcing a reset for someone else
    String currentPassword,

    @NotBlank(message = "New password is required")
    @Size(min = 8, message = "password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).+$", 
    message = "Password must contain upper, lower, digit and special character")
    String newPassword,

    @NotBlank(message = "Password  cornfirmation is required")
    String confirmPassword

) 
{}
