package com.shieldpointcapital.lrms.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.shieldpointcapital.lrms.domain.entity.StaffAccount;
import com.shieldpointcapital.lrms.dto.request.CreateStaffRequest;
import com.shieldpointcapital.lrms.dto.request.UpdateStaffRequest;
import com.shieldpointcapital.lrms.dto.response.StaffResponse;

@Mapper(componentModel = "spring")
public interface StaffMapper {

    // --- Create ---
    // staffId: generated in service (e.g. next STF-xxx sequence)
    // passwordHash: set in service after BCrypt hashing — never map plain password directly
    // tokenVersion, isActive, failedLoginCount, createdAt, createdBy: set in service
    // loanAccount: NOT set on create — a new staff member starts unassigned,
    //              assignment happens later via AssignOfficerRequest
    // lastLoginAt, lastLoginIp: no login has happened yet, nothing to set
    // passwordResetToken, tokenExpiresAt: only ever set during a password
    //              reset flow, never at creation
    // updatedAt, updatedBy: no update has happened yet, nothing to set
    @Mapping(target = "staffId", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "tokenVersion", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "failedLoginCount", ignore = true)
    @Mapping(target = "lockedUntil", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "loanAccount", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true)
    @Mapping(target = "lastLoginIp", ignore = true)
    @Mapping(target = "passwordResetToken", ignore = true)
    @Mapping(target = "tokenExpiresAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    StaffAccount toEntity(CreateStaffRequest request);

    // --- Read ---
    // isLocked is computed, not a stored column — true only while
    // lockedUntil is set AND still in the future.
    //
    // loanAccountId comes from a relationship, not a raw column, and
    // is nullable (ADMIN/MANAGER/ACCOUNTANT have no loan account) —
    // so this needs a null check, same reasoning as
    // LoanAccountResponse.assignedOfficer.
    @Mapping(target = "loanAccountId",
            expression = "java(entity.getLoanAccount() != null ? entity.getLoanAccount().getLoanAccountId() : null)")
    @Mapping(target = "isLocked",
            expression = "java(entity.getLockedUntil() != null && entity.getLockedUntil().isAfter(java.time.LocalDateTime.now()))")
    StaffResponse toResponse(StaffAccount entity);

    // --- Partial update ---
    // Null fields in the request are left untouched on the entity —
    // this is what makes UpdateStaffRequest a true partial update
    // rather than overwriting everything with nulls.
    //
    // dateOfBirth: permanently immutable after account creation, same
    //              as nationalId — never editable via profile update.
    // role, loanAccount, password: go through their own dedicated
    //              endpoints/flows, so a profile edit can never
    //              re-privilege someone or reset their security state.
    // updatedAt: the mapper CAN compute this itself, since "now" needs
    //              no outside information.
    // updatedBy: the mapper CANNOT determine this on its own — it has
    //              no access to the authenticated caller's identity.
    //              The service must resolve it (via
    //              SecurityContextHolder) and pass it in as an extra
    //              parameter.
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "staffId", ignore = true)
    @Mapping(target = "nationalId", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "loanAccount", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "tokenVersion", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "failedLoginCount", ignore = true)
    @Mapping(target = "lockedUntil", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true)
    @Mapping(target = "lastLoginIp", ignore = true)
    @Mapping(target = "passwordResetToken", ignore = true)
    @Mapping(target = "tokenExpiresAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedBy", source = "updatedBy")
    void updateEntityFromRequest(UpdateStaffRequest request, @MappingTarget StaffAccount entity, String updatedBy);
}