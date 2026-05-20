package com.bank.bankingapp.service;

import com.bank.bankingapp.dto.CreateAccountRequest;
import com.bank.bankingapp.dto.BankAccountResponse;
import com.bank.bankingapp.entity.BankAccount;
import com.bank.bankingapp.entity.User;
import com.bank.bankingapp.repository.BankAccountRepository;
import com.bank.bankingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    private final SecureRandom random = new SecureRandom();

    public BankAccountResponse createAccount(String email, CreateAccountRequest request) {
        
        // 1. Find user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // 2. Check if user already has an account of this type
        boolean accountExists = accountRepository.existsByUserAndAccountType(user, request.getAccountType());
        
        if (accountExists) {
            throw new RuntimeException("User already has a " + request.getAccountType() + " account");
        }
        
        // Optional: Check if user has any account at all
        boolean hasAnyAccount = accountRepository.existsByUser(user);
        if (hasAnyAccount) {
            System.out.println("User already has accounts, but creating another " + request.getAccountType() + " account");
        }
        
        // 3. Create account entity
        BankAccount account = new BankAccount();
        account.setUser(user);
        account.setAccountType(request.getAccountType());
        account.setBalance(BigDecimal.ZERO);
        
        String accountNumber = generateAccountNumber();
        System.out.println("Generated account number: " + accountNumber);
        account.setAccountNumber(accountNumber);
        
        // 4. Save to DB
        BankAccount saved = accountRepository.save(account);
        System.out.println("Saved account number: " + saved.getAccountNumber());
        
        // 5. Convert entity → DTO
        return mapToResponse(saved);
    }
    
    private String generateAccountNumber() {
        return "10" + (100000000 + random.nextInt(900000000));
    }
    
    private BankAccountResponse mapToResponse(BankAccount account) {
        BankAccountResponse response = new BankAccountResponse();
        response.setId(account.getId());
        response.setAccountNumber(account.getAccountNumber());
        response.setBalance(account.getBalance());
        response.setAccountType(account.getAccountType());
        return response;
    }
}