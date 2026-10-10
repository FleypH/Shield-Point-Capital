package com.shieldpointcapital.lrms.mapper;

import com.shieldpointcapital.lrms.domain.entity.ConflictLog;
import com.shieldpointcapital.lrms.dto.response.ConflictResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConflictMapper {

    ConflictResponse toResponse(ConflictLog entity);

}