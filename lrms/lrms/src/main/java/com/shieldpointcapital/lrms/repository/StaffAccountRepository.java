package com.shieldpointcapital.lrms.repository;

import com.shieldpointcapital.lrms.domain.entity.StaffAccount;
import com.shieldpointcapital.lrms.domain.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StaffAccountRepository extends JpaRepository<StaffAccount, String>{

    Optional<StaffAccount> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByNationalId(String NationalId);
    boolean existsByPhoneNumber(String phoneNumber);

    List<StaffAccount> findByRole(Role role);
    List<StaffAccount> findByIsActiveTrue();
    List<StaffAccount> findByIsActiveFalse();
    List<StaffAccount> findByRoleAndIsActive(Role role, Boolean isActive);

    Optional<StaffAccount> findByPasswordResetToken(String Token);
}
