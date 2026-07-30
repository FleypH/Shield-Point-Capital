package com.shieldpointcapital.lrms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateBorrowerRequest(

        @NotBlank(message = "National ID is required")
        @Pattern(regexp = "^\\d{2}-\\d{6,7}[A-Z]\\d{2}$", message = "National ID must match Zimbabwe format e.g. 63-123456N71")
        String idNumber,

        @NotBlank(message = "First name is required")
        @Size(max = 100)
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 100)
        String lastName,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^0\\d{9}$", message = "Phone number must be a valid Zimbabwean number e.g. 0771000001")
        String phone,

        @NotBlank(message = "Address is required")
        @Size(max = 255)
        String address

) {}