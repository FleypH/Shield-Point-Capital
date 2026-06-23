// UserInfo.java
package com.shieldpointcapital.lrms.domain.entity;

import com.shieldpointcapital.lrms.domain.converter.EncryptedStringConverter;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {

    // ─── Primary identification ──────────────────────────────────

    @Id
    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "id_number", nullable = false, length = 20)
    private String idNumber;

    @Column(name = "first_name", nullable = false )
    private String firstName;

    @Column(name = "last_name", nullable = false )
    private String lastName;

    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "phone", length = 20)
    private String phone;

    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    // ─── Audit ───────────────────────────────────────────────────

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false,
            length = 20, updatable = false)
    private String createdBy;
}