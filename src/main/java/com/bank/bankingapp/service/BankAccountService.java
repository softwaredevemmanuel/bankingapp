package com.bank.bankingapp.service;

import com.bank.bankingapp.dto.CreateAccountRequest;
import com.bank.bankingapp.entity.BankAccount;
import com.bank.bankingapp.entity.User;
import com.bank.bankingapp.repository.BankAccountRepository;
import com.bank.bankingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    public BankAccount createAccount(String email, CreateAccountRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BankAccount account = new BankAccount();
        account.setUser(user);
        account.setAccountType(request.getAccountType());
        account.setBalance(BigDecimal.ZERO);

        // generate account number
        account.setAccountNumber(generateAccountNumber());

        return accountRepository.save(account);
    }


    private String generateAccountNumber() {
        return "10" + (10000000 + new Random().nextInt(90000000));
    }
}