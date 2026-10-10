package com.shieldpointcapital.lrms.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record SyncPushResponse(

        Long syncId,   // the SyncRecord row created for this push — useful for the device to reference later

        List<String> accepted,     // localIds successfully processed
        List<RejectedItem> rejected,
        List<String> conflicts,    // localIds where a conflict was detected — resolved separately via ConflictResolutionRequest

        LocalDateTime serverTime   // anchor for the device's next pull, same as SyncPullResponse

) {
    public record RejectedItem(
            String localId,
            String reason
    ) {}
}