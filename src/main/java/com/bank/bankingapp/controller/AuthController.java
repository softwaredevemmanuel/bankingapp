package com.bank.bankingapp.controller;

import com.bank.bankingapp.dto.RegisterRequest;
import com.bank.bankingapp.dto.LoginRequest;
import com.bank.bankingapp.dto.AuthResponse;
import com.bank.bankingapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}