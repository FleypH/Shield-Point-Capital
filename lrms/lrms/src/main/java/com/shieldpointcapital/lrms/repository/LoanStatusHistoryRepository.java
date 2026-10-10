package com.shieldpointcapital.lrms.repository;

import com.shieldpointcapital.lrms.domain.entity.LoanStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoanStatusHistoryRepository
        extends JpaRepository<LoanStatusHistory, Long> {

    // Full status history for a specific loan
    List<LoanStatusHistory> findByLoanTrackingTrackingIdOrderByChangeDateAsc(
        Long trackingId
    );
}