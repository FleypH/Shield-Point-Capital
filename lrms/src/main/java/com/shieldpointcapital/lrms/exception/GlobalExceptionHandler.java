package com.shieldpointcapital.lrms.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        
        return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ErrorResponse.builder()
           .status(404)
           .error("Not Found")
           .message(ex.getMessage())
           .timestamp(LocalDateTime.now())
           .build()
        );
    }

    // 401 Unauthorized
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {

        return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(ErrorResponse.builder()
            .status(401)
            .error("Unauthorized")
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build()
        );
    }
    // Also catches spring Security's own auth exceptions
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthException( AuthenticationException ex){

        return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(ErrorResponse.builder()
            .status(401)
            .error("Unauthorised")
            .message("invalid email or password")
            .timestamp(LocalDateTime.now())
            .build()
        );
    }

    // ─── 423 Locked ──────────────────────────────────────────────

    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<ErrorResponse> handleAccountLocked(
            AccountLockedException ex) {

        return ResponseEntity
        .status(HttpStatus.LOCKED)
        .body(ErrorResponse.builder()
            .status(423)
            .error("Account Locked")
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build()
        );
    }

    // ─── 403 Forbidden ───────────────────────────────────────────

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedAction(
            UnauthorizedActionException ex) {

        return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(ErrorResponse.builder()
            .status(403)
            .error("Forbidden")
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build()
        );
    }

    // Also catches Spring Security's access denied
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(
            AccessDeniedException ex) {

        return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(ErrorResponse.builder()
            .status(403)
            .error("Forbidden")
            .message("You do not have permission to perform this action")
            .timestamp(LocalDateTime.now())
            .build()
        );
    }

    @ExceptionHandler(DeviceNotRegisteredException.class)
    public ResponseEntity<ErrorResponse> handleDeviceNotRegistered(
            DeviceNotRegisteredException ex) {

        return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(ErrorResponse.builder()
            .status(403)
            .error("Forbidden")
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build()
        );
    }

    // ─── 409 Conflict ────────────────────────────────────────────

    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(
            DuplicateRecordException ex) {

        return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(ErrorResponse.builder()
            .status(409)
            .error("Conflict")
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build()
        );
    }

    // ─── 422 Unprocessable ───────────────────────────────────────

    @ExceptionHandler(BusinessRuleViolationException.class)
    public ResponseEntity<ErrorResponse> handleBusinessRule(
            BusinessRuleViolationException ex) {

        return ResponseEntity
        .status(HttpStatus.UNPROCESSABLE_ENTITY)
        .body(ErrorResponse.builder()
            .status(422)
            .error("Business Rule Violation")
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build()
        );
    }

    // ─── 400 Bad Request ─────────────────────────────────────────

    @ExceptionHandler(SyncValidationException.class)
    public ResponseEntity<ErrorResponse> handleSyncValidation(
            SyncValidationException ex) {

        return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ErrorResponse.builder()
            .status(400)
            .error("Bad Request")
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build()
        );
    }

    //Catches @valid annotation failures on request bodies
    //return all validation errors at once
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map< String, Object>> handleValidationErrors(MethodArgumentNotValidException ex){

        Map<String, String> fieldErrors = new HashMap<>();

        for ( FieldError error : ex.getBindingResult().getFieldErrors()){
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status",400);
        response.put("error","Validation Failed");
        response.put("fields", fieldErrors);
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(response);

    }
    
    // ─── 500 Internal Server Error ───────────────────────────────

    // Catches any unexpected exception not handled above
    // Last line of defence — should never be reached
    // if all exceptions are properly handled above
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {

        return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorResponse.builder()
            .status(500)
            .error("Internal Server Error")
            .message("An unexpected error occurred. " + "Please contact your administrator.")
            .timestamp(LocalDateTime.now())
            .build()
        );
    }
}
