package com.shieldpointcapital.lrms.repository;

import com.shieldpointcapital.lrms.domain.entity.SyncRecord;
import com.shieldpointcapital.lrms.domain.enums.SyncState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SyncRecordRepository
        extends JpaRepository<SyncRecord, Long> {

    List<SyncRecord> findByDeviceId(String deviceId);
    List<SyncRecord> findByStaffId(String staffId);
    List<SyncRecord> findBySyncStatus(SyncState syncStatus);
}