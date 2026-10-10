package com.shieldpointcapital.lrms.dto.request;

import jakarta.validation.constraints.NotNull;

import com.shieldpointcapital.lrms.domain.enums.Role;


public record ChangeRoleRequest(
@NotNull(message = "New role required")
Role newRole,

//Optional - recommended for staff_audit_log.description
String reason

){}
