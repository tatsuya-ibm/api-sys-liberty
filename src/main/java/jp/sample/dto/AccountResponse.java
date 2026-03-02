package jp.sample.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 口座情報レスポンス
 */
public class AccountResponse {
    private String branchCode;
    private String accountNumber;
    private String accountName;
    private BigDecimal balance;
    private List<TransactionHistoryDto> transactionHistories;
    
    public AccountResponse() {
    }
    
    public AccountResponse(String branchCode, String accountNumber, String accountName,
                           BigDecimal balance, List<TransactionHistoryDto> transactionHistories) {
        this.branchCode = branchCode;
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = balance;
        this.transactionHistories = transactionHistories;
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
    
    public String getAccountName() {
        return accountName;
    }
    
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    public BigDecimal getBalance() {
        return balance;
    }
    
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    public List<TransactionHistoryDto> getTransactionHistories() {
        return transactionHistories;
    }
    
    public void setTransactionHistories(List<TransactionHistoryDto> transactionHistories) {
        this.transactionHistories = transactionHistories;
    }
}

// Made with Bob
