package com.shieldpointcapital.lrms.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public record ForgotPasswordRequest(
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    String email
) {}