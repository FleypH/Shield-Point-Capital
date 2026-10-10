package com.shieldpointcapital.lrms.repository;

import com.shieldpointcapital.lrms.domain.entity.StaffAuditLog;
import com.shieldpointcapital.lrms.domain.enums.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StaffAuditLogRepository
        extends JpaRepository<StaffAuditLog, Long> {

    // All actions by a specific staff member
    List<StaffAuditLog> findByStaffAccountStaffId(String staffId);

    // All actions of a specific type — e.g. all LOGIN_FAILED
    List<StaffAuditLog> findByActionType(ActionType actionType);

    // All actions by staff member of a specific type
    List<StaffAuditLog> findByStaffAccountStaffIdAndActionType(
        String staffId,
        ActionType actionType
    );
}