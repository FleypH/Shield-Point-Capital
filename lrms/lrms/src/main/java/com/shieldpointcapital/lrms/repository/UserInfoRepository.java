package com.shieldpointcapital.lrms.repository;

import com.shieldpointcapital.lrms.domain.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserInfoRepository
        extends JpaRepository<UserInfo, String> {

    boolean existsByIdNumber(String idNumber);

    Optional<UserInfo> findByIdNumber(String idNumber);
}