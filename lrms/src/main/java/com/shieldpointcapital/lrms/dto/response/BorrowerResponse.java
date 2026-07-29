package com.shieldpointcapital.lrms.dto.response;

import java.time.LocalDateTime;
public record BorrowerResponse(
    String  borrowerId,
    String firstName,
    String lastName,
    String address,
    String phoneNumber,
    LocalDateTime createdAt,
    String createdByName   // resolved by the service via a second StaffAccount lookup, same pattern as recordedByName

) {}
