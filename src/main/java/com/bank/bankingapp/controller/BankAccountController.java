package com.bank.bankingapp.controller;

import com.bank.bankingapp.dto.CreateAccountRequest;
import com.bank.bankingapp.entity.BankAccount;
import com.bank.bankingapp.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService accountService;

    @PostMapping
    public BankAccount createAccount(
            Authentication authentication,
            @RequestBody CreateAccountRequest request
    ) {

        String email = authentication.getName();

        return accountService.createAccount(email, request);
    }
}