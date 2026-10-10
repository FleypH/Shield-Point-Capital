package com.shieldpointcapital.lrms.repository;

import com.shieldpointcapital.lrms.domain.entity.CompanyLedger;
import com.shieldpointcapital.lrms.domain.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompanyLedgerRepository
        extends JpaRepository<CompanyLedger, Long> {

    // All ledger entries for a specific loan account
    List<CompanyLedger> findByLoanAccountLoanAccountId(String loanAccountId);

    // All ledger entries for a specific loan
    List<CompanyLedger> findByLoanTrackingTrackingId(Long trackingId);

    // All entries of a specific event type
    List<CompanyLedger> findByEventType(EventType eventType);
}