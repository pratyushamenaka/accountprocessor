package com.account.processor.app;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * POJO for Account Data
 */

public class AccountData {


    private String transactionId;
    private String fromAccountId;
    private String toAccountId;
    private Date createdAt;
    private Double amount;
    private String transactionType;
    private String relatedTransaction;


    public AccountData(Map<String, String> row) {
        this.transactionId = row.get("transactionId");
        this.fromAccountId = row.get("fromAccountId");
        this.toAccountId = row.get("toAccountId");
        try {
            this.createdAt = AccountDataUtil.convertDateTime(row.get("createdAt"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.amount = Double.parseDouble(row.get("amount"));
        this.transactionType = row.get("transactionType");
        this.relatedTransaction = row.get("relatedTransaction");
    }

    public AccountData(String transactionId,String fromAccountId, String toAccountId, String createdAt, Double amount, String transactionType, String relatedTransaction ) {
        this.transactionId = transactionId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        try {
            this.createdAt = AccountDataUtil.convertDateTime(createdAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.amount = amount;
        this.transactionType = transactionType;
        this.relatedTransaction = relatedTransaction;
    }


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getRelatedTransaction() {
        return relatedTransaction;
    }

    public void setRelatedTransaction(String relatedTransaction) {
        this.relatedTransaction = relatedTransaction;
    }

    @Override
    public String toString() {
        return "AccountData{" +
                "transactionId='" + transactionId + '\'' +
                ", fromAccountId='" + fromAccountId + '\'' +
                ", toAccountId='" + toAccountId + '\'' +
                ", createdAt=" + createdAt +
                ", amount=" + amount +
                ", transactionType='" + transactionType + '\'' +
                ", relatedTransaction='" + relatedTransaction + '\'' +
                '}';
    }

}
