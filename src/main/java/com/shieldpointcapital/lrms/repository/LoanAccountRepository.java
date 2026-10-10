package com.shieldpointcapital.lrms.repository;

import com.shieldpointcapital.lrms.domain.entity.LoanAccount;
import com.shieldpointcapital.lrms.domain.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoanAccountRepository
        extends JpaRepository<LoanAccount, String> {

    List<LoanAccount> findByAccountStatus(AccountStatus status);

    List<LoanAccount> findByAssignedOfficerStaffId(String staffId);

    boolean existsByAccountName(String accountName);
}