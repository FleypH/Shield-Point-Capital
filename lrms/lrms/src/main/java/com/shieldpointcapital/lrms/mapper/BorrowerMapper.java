package com.shieldpointcapital.lrms.mapper;

import com.shieldpointcapital.lrms.domain.entity.UserInfo;
import com.shieldpointcapital.lrms.dto.request.CreateBorrowerRequest;
import com.shieldpointcapital.lrms.dto.response.BorrowerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BorrowerMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    UserInfo toEntity(CreateBorrowerRequest request);

    @Mapping(target = "createdByName", ignore = true)
    BorrowerResponse toResponse(UserInfo entity);

    default BorrowerResponse toResponse(UserInfo entity, String createdByName) {
        BorrowerResponse base = toResponse(entity);
        return new BorrowerResponse(
                base.idNumber(),
                base.firstName(),
                base.lastName(),
                base.phone(),
                base.address(),
                base.createdAt(),
                createdByName
        );
    }
}