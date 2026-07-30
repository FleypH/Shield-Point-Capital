package com.shieldpointcapital.lrms.mapper;

import com.shieldpointcapital.lrms.domain.entity.StaffAccount;
import com.shieldpointcapital.lrms.dto.request.CreateStaffrequest;
import com.shieldpointcapital.lrms.dto.request.UpdateStaffRequest;
import com.shieldpointcapital.lrms.dto.response.Staffresponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface StaffMapper {

    // --- Create ---
    // staffId: generated in service (e.g. next STF-xxx sequence)
    // passwordHash: set in service after BCrypt hashing — never map plain password directly
    // tokenVersion, isActive, failedLoginCount, createdAt, createdBy: set in service
    @Mapping(target = "staffId", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "tokenVersion", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "failedLoginCount", ignore = true)
    @Mapping(target = "lockedUntil", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    StaffAccount toEntity(CreateStaffrequest request);

    // --- Read ---
    @Mapping(target = "isLocked",
            expression = "java(entity.getLockedUntil() != null && entity.getLockedUntil().isAfter(java.time.LocalDateTime.now()))")
    Staffresponse toResponse(StaffAccount entity);

    // --- Partial update ---
    // Null fields in the request are left untouched on the entity.
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "staffId", ignore = true)
    @Mapping(target = "nationalId", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "loanAccountId", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "tokenVersion", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "failedLoginCount", ignore = true)
    @Mapping(target = "lockedUntil", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    void updateEntityFromRequest(UpdateStaffRequest request, @MappingTarget StaffAccount entity);
}