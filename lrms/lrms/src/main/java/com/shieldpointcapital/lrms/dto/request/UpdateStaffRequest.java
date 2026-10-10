package com.shieldpointcapital.lrms.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
 
/**
 * Partial update — every field nullable. Only non-null fields are applied.
 * Deliberately excludes: nationalId (identity, immutable), role (use
 * ChangeRoleRequest), loanAccountId (use AssignOfficerRequest), password
 * (use PasswordResetRequest). Keeping those out of this DTO stops a single
 * "update profile" call from silently re-privileging or reassigning someone.
 */
public record UpdateStaffRequest(

@Size(max = 100)
String firstName,

@Size(max = 100)
String lastName,

@Size(max = 255)
String address,

@Email(message = "Email must be a valid email address")
String email,

@Pattern(regexp =" ^0\\d{9}$", message = "Phone number must be a valid Zimbabwean number e.g. 0771000001")
String phoneNumber,

@Size(max = 100)
String jobTitle

) {}
