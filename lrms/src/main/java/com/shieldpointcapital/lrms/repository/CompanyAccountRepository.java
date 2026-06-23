package com.shieldpointcapital.lrms.repository;

import com.shieldpointcapital.lrms.domain.entity.CompanyAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyAccountRepository
        extends JpaRepository<CompanyAccount, Byte> {
    // No custom methods needed
    // findById((byte) 1) fetches the single company record
}