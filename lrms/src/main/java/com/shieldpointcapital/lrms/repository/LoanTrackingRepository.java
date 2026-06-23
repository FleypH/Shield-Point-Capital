package com.shieldpointcapital.lrms.repository;

import com.shieldpointcapital.lrms.domain.entity.LoanTracking;
import com.shieldpointcapital.lrms.domain.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoanTrackingRepository
        extends JpaRepository<LoanTracking, Long> {

    // Find all loans for a borrower
    List<LoanTracking> findByBorrowerIdNumber(String idNumber);

    // Find all loans under a specific loan account
    List<LoanTracking> findByLoanAccountLoanAccountId(String loanAccountId);

    // Find loans by status
    List<LoanTracking> findByLoanStatus(LoanStatus status);

    // Check if borrower has any active or defaulted loans
    // Used before approving a new loan
    boolean existsByBorrowerIdNumberAndLoanStatusIn(
        String idNumber,
        List<LoanStatus> statuses
    );

    // Find all overdue loans — days_past_due > 0 and still active
    @Query("SELECT lt FROM LoanTracking lt " +
           "WHERE lt.daysPastDue > 0 " +
           "AND lt.loanStatus = 'ACTIVE'")
    List<LoanTracking> findOverdueLoans();

    // Find all loans for a borrower under a specific account
    List<LoanTracking> findByBorrowerIdNumberAndLoanAccountLoanAccountId(
        String idNumber,
        String loanAccountId
    );
}