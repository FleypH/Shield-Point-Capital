package com.shieldpointcapital.lrms.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.Valid;

import java.util.List;

public record SyncPushRequest(

        /* deviceId, staffId — not fields here. Derived from JWT claims
           by the service, then stamped onto the SyncRecord summary row
           it creates for this push. */

        @NotEmpty(message = "At least one change is required")
        @Valid  // ensures each SyncPayload inside the list gets its own validation applied
        List<SyncPayload> changes

) {}