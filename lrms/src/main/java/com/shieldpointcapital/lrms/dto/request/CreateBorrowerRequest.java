package com.shieldpointcapital.lrms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateBorrowerRequest(
    @NotBlank(message = "Borrower's National ID number required")
    @Pattern(regexp = "^\\d{2}-\\d{6,7}[A-Z]\\d{2}$", message = "National ID must match Zimbabwe format e.g. 63-123456N71")
    String  borrowerId,

    @NotBlank(message = "Borrower name required")
    @Size(max = 100)
    String firstName,

    @NotBlank(message = "last name required")
    @Size(max = 100)
    String lastName,

    @NotBlank(message = "address required")
    @Size(max = 255)
    String address,

    @NotBlank(message = "phone number required")
    @Pattern(regexp =" ^0\\d{9}$", message = "Phone number must be a valid Zimbabwean number e.g. 0771000001")
    String phoneNumber
) {}
