package com.shieldpointcapital.lrms.config;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.shieldpointcapital.lrms.domain.entity.StaffAccount;
import com.shieldpointcapital.lrms.domain.enums.Role;
import com.shieldpointcapital.lrms.repository.StaffAccountRepository;

@Component
@Profile("dev")   // runs only with the "dev" profile, never in production
public class DevDataSeeder implements CommandLineRunner {

    private final StaffAccountRepository staffAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public DevDataSeeder(StaffAccountRepository staffAccountRepository,
                         PasswordEncoder passwordEncoder) {
        this.staffAccountRepository = staffAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Spring calls run() once, right after startup finishes.
    @Override
    public void run(String... args) {

        // Skip if the admin already exists, so restarts don't duplicate it.
        if (staffAccountRepository.existsById("STF-001")
                || staffAccountRepository.existsByEmail("chamisaphil@gmail.com")) {
            return;
        }

        StaffAccount admin = StaffAccount.builder()
                .staffId("STF-001")
                .nationalId("63-123456N71")      // encrypted automatically on save
                .firstName("System")
                .lastName("Admin")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("Harare, Zimbabwe")     // encrypted automatically on save
                .email("chamisaphil@gmail.com")
                .phoneNumber("0771000001")       // encrypted automatically on save
                .jobTitle("System Administrator")
                .role(Role.ADMIN)
                .passwordHash(passwordEncoder.encode("Admin@1234"))  // BCrypt hash
                .tokenVersion(0)
                .isActive(true)
                .failedLoginCount((byte) 0)
                .createdAt(LocalDateTime.now())
                .createdBy("SYSTEM")
                .build();

        staffAccountRepository.save(admin);
    }
}