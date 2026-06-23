package com.shieldpointcapital.lrms.repository;

import com.shieldpointcapital.lrms.domain.entity.PaymentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentLogRepository
        extends JpaRepository<PaymentLog, Long> {

    // All payments for a specific loan
    List<PaymentLog> findByLoanTrackingTrackingId(Long trackingId);

    // All payments recorded by a specific staff member
    List<PaymentLog> findByRecordedBy(String staffId);
}