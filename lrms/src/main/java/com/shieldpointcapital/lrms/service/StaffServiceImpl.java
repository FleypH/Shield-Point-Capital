package com.shieldpointcapital.lrms.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shieldpointcapital.lrms.domain.entity.LoanAccount;
import com.shieldpointcapital.lrms.domain.entity.StaffAccount;
import com.shieldpointcapital.lrms.domain.enums.Role;
import com.shieldpointcapital.lrms.dto.request.ChangeRoleRequest;
import com.shieldpointcapital.lrms.dto.request.CreateStaffRequest;
import com.shieldpointcapital.lrms.dto.request.UpdateStaffRequest;
import com.shieldpointcapital.lrms.dto.response.StaffResponse;

import com.shieldpointcapital.lrms.exception.BadRequestException;
import com.shieldpointcapital.lrms.exception.BusinessRuleViolationException;
import com.shieldpointcapital.lrms.exception.DuplicateRecordException;
import com.shieldpointcapital.lrms.exception.ResourceNotFoundException;
import com.shieldpointcapital.lrms.exception.UnauthorizedActionException;

import com.shieldpointcapital.lrms.mapper.StaffMapper;
import com.shieldpointcapital.lrms.repository.LoanAccountRepository;
import com.shieldpointcapital.lrms.repository.StaffAccountRepository;;

@Service
@SuppressWarnings("null")
public class StaffServiceImpl implements StaffService {
    
    private final StaffAccountRepository staffAccountRepository;
    private final LoanAccountRepository loanAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final StaffMapper staffMapper;
    private final IdGenerator idGenerator;

    public StaffServiceImpl( StaffAccountRepository staffAccountRepository, 
                            LoanAccountRepository loanAccountRepository,
                            PasswordEncoder passwordEncoder,
                            StaffMapper staffMapper,
                            IdGenerator idGenerator) {
        this.staffAccountRepository = staffAccountRepository;
        this.loanAccountRepository = loanAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.staffMapper = staffMapper;
        this.idGenerator = idGenerator;
    }
    // createStaff
    @Override 
    @Transactional 
    public StaffResponse createStaff(CreateStaffRequest request) {
        String actorId = requireAdmin();

        if (staffAccountRepository.existsByEmail(request.email())) {
            throw new DuplicateRecordException("A staff with this email already exists");
        }

        StaffAccount staff = new StaffAccount();
        staff.setStaffId(idGenerator.nextStaffId());
        staff.setNationalId(request.nationalId());
        staff.setFirstName(request.firstName());
        staff.setLastName(request.lastName());
        staff.setDateOfBirth(request.dateOfBirth());
        staff.setAddress(request.address());
        staff.setEmail(request.email());
        staff.setPhoneNumber(request.phoneNumber());
        staff.setJobTitle(request.jobTitle());
        staff.setRole(request.role());
        staff.setPasswordHash(passwordEncoder.encode(request.password()));
        staff.setTokenVersion(0);
        staff.setIsActive(true);
        staff.setFailedLoginCount((byte) 0);
        staff.setCreatedAt(LocalDateTime.now());
        staff.setCreatedBy(actorId);

        return staffMapper.toResponse(staffAccountRepository.save(staff));
    }
    // getStaff
    public StaffResponse getStaff(String staffId) {
        requireAdmin();
        return staffMapper.toResponse(findOrThrow(staffId));
    }

    // listStaff
    @Override 
    @Transactional(readOnly = true)
    public List<StaffResponse> listStaff() {
        requireAdmin();
        return staffAccountRepository.findAll().stream()
                .map(staffMapper::toResponse)
                .toList();
    }

    // updateStaff 
    // dateOfBirth and nationalId are immutable, so they are not touched here.
    @Override
    @Transactional
    public StaffResponse updateStaff(String staffId, UpdateStaffRequest request) {
        String actorId = requireAdmin();
        StaffAccount staff = findOrThrow(staffId);

        staffAccountRepository.findByEmail(request.email()).ifPresent(other -> {
            if (!other.getStaffId().equals(staffId)) {
            throw new DuplicateRecordException("Email is already used by another staff member");
        }
    });
    staff.setFirstName(request.firstName());
    staff.setLastName(request.lastName());
    staff.setAddress(request.address());
    staff.setEmail(request.email());
    staff.setPhoneNumber(request.phoneNumber());

    staff.setJobTitle(request.jobTitle());
    staff.setUpdatedAt(LocalDateTime.now());
    staff.setUpdatedBy(actorId);
    return staffMapper.toResponse(staffAccountRepository.save(staff));
    }

    // changeRole 
    @Override
    @Transactional
    public StaffResponse changeRole(String staffId, ChangeRoleRequest request) {
        String actorId = requireAdmin();
        if (actorId.equals(staffId)) 
            { throw new BusinessRuleViolationException("You cannot change your own role");}
       
        StaffAccount staff = findOrThrow(staffId);
        staff.setRole(request.newRole());
        staff.setTokenVersion(staff.getTokenVersion() + 1); // old tokens carry the old role
        staff.setUpdatedAt(LocalDateTime.now());
        staff.setUpdatedBy(actorId);
        
        return staffMapper.toResponse(staffAccountRepository.save(staff));
    }

    @Override 
    @Transactional 
    public void deactivateStaff(String staffId) {
        String actorId = requireAdmin();

        if (actorId.equals(staffId)) {
            throw new BusinessRuleViolationException("You cannot deactivate your own account");
        }

        StaffAccount staff = findOrThrow(staffId);

        if (!staff.getIsActive()) {
            throw new BusinessRuleViolationException("Staff member is already deactivated");
        }

        staff.setIsActive(false);
        staff.setTokenVersion(staff.getTokenVersion() + 1); 
        staff.setUpdatedAt(LocalDateTime.now());
        staff.setUpdatedBy(actorId);

        staffAccountRepository.save(staff);
    }

    // assignAccount/remove account

    @Override 
    @Transactional 
    public StaffResponse assignAccount(String staffId, String loanAccountId) {
        String actorId = requireAdmin();
        StaffAccount staff = findOrThrow(staffId);

        LoanAccount account = loanAccountRepository.findById(loanAccountId)
            .orElseThrow(() -> new ResourceNotFoundException("Loan account not found"));

        staff.setLoanAccount(account);
        staff.setTokenVersion(staff.getTokenVersion() + 1);
        staff.setUpdatedBy(actorId);
    
        return staffMapper.toResponse(staffAccountRepository.save(staff));
    }

    @Override
    @Transactional
    public StaffResponse removeAccount(String staffId) {
        String actorId = requireAdmin();
        StaffAccount staff = findOrThrow(staffId);

        if (staff.getLoanAccount() == null) {
            throw new BadRequestException("Staff member had no assigned loan account");
        }

        staff.setLoanAccount(null);
        staff.setTokenVersion(staff.getTokenVersion() + 1);
        staff.setUpdatedAt(LocalDateTime.now());
        staff.setUpdatedBy(actorId);

        return staffMapper.toResponse(staffAccountRepository.save(staff));

    }

    // helpers
    private StaffAccount findOrThrow(String staffId) {
        return staffAccountRepository.findById(staffId)
                    .orElseThrow(() -> new ResourceNotFoundException("Staff not found"));             
    }

    // Returns the caller's staffId if they are an active ADMIN, otherwisw throws 403
    private String requireAdmin() {
        String actorId = SecurityContextHolder.getContext().getAuthentication().getName();
        StaffAccount actor = findOrThrow(actorId);
        if (actor.getRole() != Role.ADMIN) {
            throw new UnauthorizedActionException("Only an adminstrator can perform this action");
        }
        return actorId;
    }

}
