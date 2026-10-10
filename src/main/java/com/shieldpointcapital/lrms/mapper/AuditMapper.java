package com.shieldpointcapital.lrms.mapper;

import com.shieldpointcapital.lrms.domain.entity.StaffAuditLog;
import com.shieldpointcapital.lrms.dto.response.AuditLogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuditMapper {

    // staffAccount is a real @ManyToOne relationship (confirmed from
    // the entity), non-nullable per its @JoinColumn — so both fields
    // can be pulled by direct traversal, no second repository lookup
    // and no null check needed, unlike LoanAccount.assignedOfficer.
    @Mapping(source = "staffAccount.staffId", target = "staffId")
    @Mapping(target = "staffName",
            expression = "java(entity.getStaffAccount().getFirstName() + \" \" + entity.getStaffAccount().getLastName())")
    AuditLogResponse toResponse(StaffAuditLog entity);

}