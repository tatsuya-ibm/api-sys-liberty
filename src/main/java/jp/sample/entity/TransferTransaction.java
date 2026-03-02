package jp.sample.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 振込トランザクションエンティティ
 */
@Entity
@Table(name = "transfer_transaction")
public class TransferTransaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "dest_bank_code", length = 4, nullable = false)
    private String destBankCode;
    
    @Column(name = "dest_branch_code", length = 3, nullable = false)
    private String destBranchCode;
    
    @Column(name = "dest_account_number", length = 7, nullable = false)
    private String destAccountNumber;
    
    @Column(name = "amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal amount;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    public TransferTransaction() {
    }
    
    public TransferTransaction(String destBankCode, String destBranchCode, 
                              String destAccountNumber, BigDecimal amount) {
        this.destBankCode = destBankCode;
        this.destBranchCode = destBranchCode;
        this.destAccountNumber = destAccountNumber;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDestBankCode() {
        return destBankCode;
    }
    
    public void setDestBankCode(String destBankCode) {
        this.destBankCode = destBankCode;
    }
    
    public String getDestBranchCode() {
        return destBranchCode;
    }
    
    public void setDestBranchCode(String destBranchCode) {
        this.destBranchCode = destBranchCode;
    }
    
    public String getDestAccountNumber() {
        return destAccountNumber;
    }
    
    public void setDestAccountNumber(String destAccountNumber) {
        this.destAccountNumber = destAccountNumber;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

// Made with Bob
