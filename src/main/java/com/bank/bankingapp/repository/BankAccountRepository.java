package com.bank.bankingapp.repository;

import com.bank.bankingapp.entity.BankAccount;
import com.bank.bankingapp.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    
    // Check if user has an account of specific type
    boolean existsByUserAndAccountType(User user, String accountType);
    
    // Check if user has any account at all
    boolean existsByUser(User user);
    
    // Get all accounts for a user
    List<BankAccount> findByUser(User user);
    
    // Get specific account type for a user
    Optional<BankAccount> findByUserAndAccountType(User user, String accountType);
}