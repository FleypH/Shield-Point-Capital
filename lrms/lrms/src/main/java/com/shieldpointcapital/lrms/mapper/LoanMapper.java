package com.shieldpointcapital.lrms.mapper;

import com.shieldpointcapital.lrms.domain.entity.LoanTracking;
import com.shieldpointcapital.lrms.dto.response.LoanResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    @Mapping(target = "borrowerName",
            expression = "java(entity.getBorrower().getFirstName() + \" \" + entity.getBorrower().getLastName())")
    @Mapping(source = "loanAccount.loanAccountId", target = "loanAccountId")
    LoanResponse toResponse(LoanTracking entity);

}