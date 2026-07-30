package com.shieldpointcapital.lrms.mapper;

import com.shieldpointcapital.lrms.domain.entity.CompanyLedger;
import com.shieldpointcapital.lrms.dto.response.LedgerEntryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LedgerMapper {

    // loanAccount and loanTracking are both @ManyToOne relationships on
    // CompanyLedger, and — per the entity — both are non-nullable
    // (every ledger entry is tied to a specific account and loan), so
    // no null check needed here, unlike LoanAccount.assignedOfficer.
    //
    // recordedByName isn't a column — recordedBy is a raw staffId
    // String, same as PaymentLog, so it needs the enrich-after-mapping
    // overload.
    @Mapping(source = "loanAccount.loanAccountId", target = "loanAccountId")
    @Mapping(source = "loanTracking.trackingId", target = "trackingId")
    @Mapping(target = "recordedByName", ignore = true)
    LedgerEntryResponse toResponse(CompanyLedger entity);

    default LedgerEntryResponse toResponse(CompanyLedger entity, String recordedByName) {
        LedgerEntryResponse base = toResponse(entity);
        return new LedgerEntryResponse(
                base.ledgerId(),
                base.loanAccountId(),
                base.trackingId(),
                base.eventType(),
                base.eventDate(),
                recordedByName,
                base.recordedAt(),
                base.capitalEffect(),
                base.principalComponent(),
                base.interestComponent(),
                base.penaltyComponent(),
                base.lossAmount(),
                base.revenueEffect(),
                base.subAccountBalanceAfter(),
                base.masterBalanceAfter(),
                base.notes()
        );
    }
}