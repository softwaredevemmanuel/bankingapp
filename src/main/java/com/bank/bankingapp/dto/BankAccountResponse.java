package com.bank.bankingapp.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccountResponse {

    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private String accountType;
}