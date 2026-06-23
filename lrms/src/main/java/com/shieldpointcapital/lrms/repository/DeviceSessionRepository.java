package com.shieldpointcapital.lrms.repository;

import com.shieldpointcapital.lrms.domain.entity.DeviceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceSessionRepository
        extends JpaRepository<DeviceSession, Long> {

    Optional<DeviceSession> findByDeviceIdAndStaffId(
        String deviceId,
        String staffId
    );

    List<DeviceSession> findByStaffId(String staffId);
    List<DeviceSession> findByIsActiveTrue();

    boolean existsByDeviceIdAndStaffId(
        String deviceId,
        String staffId
    );
}