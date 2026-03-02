package jp.sample.dto;

import java.math.BigDecimal;

/**
 * 入金リクエスト
 */
public class DepositRequest {
    private String branchCode;
    private String accountNumber;
    private BigDecimal amount;
    
    public DepositRequest() {
    }
    
    public DepositRequest(String branchCode, String accountNumber, BigDecimal amount) {
        this.branchCode = branchCode;
        this.accountNumber = accountNumber;
        this.amount = amount;
    }
    
    // Getters and Setters
    public String getBranchCode() {
        return branchCode;
    }
    
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

// Made with Bob
