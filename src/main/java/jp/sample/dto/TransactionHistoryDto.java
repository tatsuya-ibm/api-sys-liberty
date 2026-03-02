package jp.sample.dto;

import java.math.BigDecimal;

/**
 * 入出金履歴DTO
 */
public class TransactionHistoryDto {
    private String transactionDate;
    private BigDecimal amount;
    private String comment;

    public TransactionHistoryDto() {
    }

    public TransactionHistoryDto(String transactionDate, BigDecimal amount, String comment) {
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.comment = comment;
    }

    // Getters and Setters
    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

// Made with Bob