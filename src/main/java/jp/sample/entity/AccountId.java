package jp.sample.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * 口座の複合主キークラス
 */
public class AccountId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String branchCode;
    private String accountNumber;
    
    public AccountId() {
    }
    
    public AccountId(String branchCode, String accountNumber) {
        this.branchCode = branchCode;
        this.accountNumber = accountNumber;
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountId accountId = (AccountId) o;
        return Objects.equals(branchCode, accountId.branchCode) &&
               Objects.equals(accountNumber, accountId.accountNumber);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(branchCode, accountNumber);
    }
}

// Made with Bob
