package com.bank.bankingapp.controller;

import com.bank.bankingapp.dto.CreateAccountRequest;
import com.bank.bankingapp.dto.BankAccountResponse;
import com.bank.bankingapp.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BankAccountResponse createAccount(
            Authentication authentication,
            @RequestBody CreateAccountRequest request) {
        
        // DEBUGGING STARTS HERE
        System.out.println("========== DEBUG: BankAccountController ==========");
        
        // 1. Check if authentication is null
        if (authentication == null) {
            System.err.println("ERROR: Authentication is NULL!");
            throw new RuntimeException("Authentication object is null - User not authenticated");
        }
        
        // 2. Check authentication details
        System.out.println("Authentication object: " + authentication.getClass().getName());
        System.out.println("Is authenticated: " + authentication.isAuthenticated());
        System.out.println("Principal: " + authentication.getPrincipal());
        System.out.println("Credentials: " + authentication.getCredentials());
        System.out.println("Authorities: " + authentication.getAuthorities());
        
        // 3. Get and check email
        String email = authentication.getName();
        System.out.println("Email from authentication: '" + email + "'");
        
        if (email == null || email.isEmpty()) {
            System.err.println("ERROR: Email is null or empty!");
            throw new RuntimeException("Email is null or empty");
        }
        
        // 4. Check request body
        System.out.println("Request body: " + request);
        if (request == null) {
            System.err.println("ERROR: Request body is NULL!");
            throw new RuntimeException("Request body is null");
        }
        
        System.out.println("Account type from request: '" + request.getAccountType() + "'");
        
        if (request.getAccountType() == null || request.getAccountType().isEmpty()) {
            System.err.println("ERROR: Account type is null or empty!");
            throw new RuntimeException("Account type is required");
        }
        
        // 5. Call service
        System.out.println("Calling service.createAccount with email: " + email);
        BankAccountResponse response = accountService.createAccount(email, request);
        
        // 6. Check response from service
        System.out.println("Response from service: " + response);
        if (response == null) {
            System.err.println("ERROR: Service returned NULL response!");
            throw new RuntimeException("Service returned null response");
        }
        
        System.out.println("Response ID: " + response.getId());
        System.out.println("Response Account Number: " + response.getAccountNumber());
        System.out.println("Response Balance: " + response.getBalance());
        System.out.println("Response Account Type: " + response.getAccountType());
        
        System.out.println("========== DEBUG END ==========");
        
        return response;
    }
    
    // Optional: Add exception handler to see errors
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        System.err.println("Exception caught in controller: " + e.getMessage());
        e.printStackTrace();
        return "Error: " + e.getMessage();
    }
}