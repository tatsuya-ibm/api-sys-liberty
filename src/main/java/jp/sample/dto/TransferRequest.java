package jp.sample.dto;

import java.math.BigDecimal;

/**
 * 振込リクエスト
 */
public class TransferRequest {
    private String destBankCode;
    private String destBranchCode;
    private String destAccountNumber;
    private BigDecimal amount;
    
    public TransferRequest() {
    }
    
    public TransferRequest(String destBankCode, String destBranchCode, 
                          String destAccountNumber, BigDecimal amount) {
        this.destBankCode = destBankCode;
        this.destBranchCode = destBranchCode;
        this.destAccountNumber = destAccountNumber;
        this.amount = amount;
    }
    
    // Getters and Setters
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
}

// Made with Bob
