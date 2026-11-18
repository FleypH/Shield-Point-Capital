package com.shieldpointcapital.lrms.service;

import java.util.List;

import com.shieldpointcapital.lrms.dto.request.ChangeRoleRequest;
import com.shieldpointcapital.lrms.dto.request.CreateStaffRequest;
import com.shieldpointcapital.lrms.dto.request.UpdateStaffRequest;
import com.shieldpointcapital.lrms.dto.response.StaffResponse;



public interface StaffService {
    StaffResponse createStaff( CreateStaffRequest request);
    StaffResponse getStaff(String staffId);
    List<StaffResponse> listStaff();
    StaffResponse updateStaff(String staffId, UpdateStaffRequest request);
    StaffResponse changeRole(String staffId, ChangeRoleRequest request);
    void deactivateStaff(String staffId);
    StaffResponse assignAccount(String staffId, String loanAccountId);
    StaffResponse removeAccount(String staffId);
}
