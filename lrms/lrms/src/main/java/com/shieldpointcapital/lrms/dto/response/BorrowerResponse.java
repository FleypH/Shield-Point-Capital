package com.shieldpointcapital.lrms.dto.response;

import java.time.LocalDateTime;

public record BorrowerResponse(

        String idNumber,
        String firstName,
        String lastName,
        String phone,
        String address,

        LocalDateTime createdAt,
        String createdByName

) {}