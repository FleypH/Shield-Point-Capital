package com.shieldpointcapital.lrms.dto.response;

public record BorrowerResponse(
    String  borrowerId,
    String firstName,
    String lastName,
    String address,
    String phoneNumber
) {}
