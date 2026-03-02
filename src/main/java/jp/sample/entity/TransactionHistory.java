package jp.sample.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 入出金履歴エンティティ
 */
@Entity
@Table(name = "transaction_history")
public class TransactionHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "branch_code", length = 3, nullable = false)
    private String branchCode;
    
    @Column(name = "account_number", length = 7, nullable = false)
    private String accountNumber;
    
    @Column(name = "transaction_type", length = 10, nullable = false)
    private String transactionType;
    
    @Column(name = "amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal amount;
    
    @Column(name = "balance_after", precision = 15, scale = 2, nullable = false)
    private BigDecimal balanceAfter;
    
    @Column(name = "transaction_at", nullable = false)
    private LocalDateTime transactionAt;
    
    public TransactionHistory() {
    }
    
    public TransactionHistory(String branchCode, String accountNumber, String transactionType, 
                            BigDecimal amount, BigDecimal balanceAfter) {
        this.branchCode = branchCode;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.transactionAt = LocalDateTime.now();
    }
    
    @PrePersist
    protected void onCreate() {
        if (transactionAt == null) {
            transactionAt = LocalDateTime.now();
        }
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public String getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }
    
    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
    
    public LocalDateTime getTransactionAt() {
        return transactionAt;
    }
    
    public void setTransactionAt(LocalDateTime transactionAt) {
        this.transactionAt = transactionAt;
    }
}

// Made with Bob
