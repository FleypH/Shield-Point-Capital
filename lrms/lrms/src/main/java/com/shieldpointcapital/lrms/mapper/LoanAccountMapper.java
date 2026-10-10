package com.shieldpointcapital.lrms.mapper;

import com.shieldpointcapital.lrms.domain.entity.LoanAccount;
import com.shieldpointcapital.lrms.dto.response.LoanAccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanAccountMapper {

    // assignedOfficer is a real @ManyToOne relationship, but it's
    // NULLABLE (an account can be unassigned) — unlike LoanTracking's
    // borrower, which is always present. Both expressions need a null
    // check first, or an unassigned account throws a
    // NullPointerException the moment this mapper runs.
    @Mapping(target = "assignedOfficerId",
            expression = "java(entity.getAssignedOfficer() != null ? entity.getAssignedOfficer().getStaffId() : null)")
    @Mapping(target = "assignedOfficerName",
            expression = "java(entity.getAssignedOfficer() != null ? entity.getAssignedOfficer().getFirstName() + \" \" + entity.getAssignedOfficer().getLastName() : null)")
    LoanAccountResponse toResponse(LoanAccount entity);

}