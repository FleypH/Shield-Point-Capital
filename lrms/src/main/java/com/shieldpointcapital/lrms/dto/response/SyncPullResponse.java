package com.shieldpointcapital.lrms.dto.response;

import com.shieldpointcapital.lrms.domain.enums.EntityType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record SyncPullResponse(

        List<SyncPullItem> changes,

        // Anchor for the device's NEXT pull request — same pattern as
        // your API doc's serverTime field. The device stores this and
        // sends it back as `since` next time, not its own clock, so
        // clock drift between device and server never causes missed
        // changes.
        LocalDateTime serverTime

) {
    public record SyncPullItem(

            EntityType entityType,
            String entityId,          // the server's real ID, not a local device ID

            // Flexible shape, same reasoning as SyncPayload.data — a
            // borrower record and a payment record don't share fields.
            Map<String, Object> data,

            LocalDateTime updatedAt

    ) {}
}