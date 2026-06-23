package com.shieldpointcapital.lrms.repository;

import com.shieldpointcapital.lrms.domain.entity.ConflictLog;
import com.shieldpointcapital.lrms.domain.enums.ConflictResolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConflictLogRepository
        extends JpaRepository<ConflictLog, Long> {

    List<ConflictLog> findByDeviceId(String deviceId);
    List<ConflictLog> findByStaffId(String staffId);

    // Unresolved conflicts — resolution is null
    List<ConflictLog> findByResolutionIsNull();

    List<ConflictLog> findByResolution(ConflictResolution resolution);
}