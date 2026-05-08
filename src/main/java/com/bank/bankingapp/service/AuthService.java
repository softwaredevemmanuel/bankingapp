package com.bank.bankingapp.service;

import com.bank.bankingapp.dto.RegisterRequest;
import com.bank.bankingapp.entity.User;
import com.bank.bankingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String register(RegisterRequest request) {

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        // IMPORTANT: encrypt password
        user.setPassword(encoder.encode(request.getPassword()));

        user.setRole("USER");

        userRepository.save(user);

        return "User registered successfully";
    }
}