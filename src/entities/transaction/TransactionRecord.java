package entities.transaction;

import enums.TransactionStatus;

public abstract class TransactionRecord {
    private double amount;
    private String dateTime;
    private TransactionStatus status;

    protected TransactionRecord(double amount, String dateTime){
        this.amount = amount;
        this.dateTime = dateTime;
        this.status = TransactionStatus.PENDING;
    }

    public double getAmount() {
        return amount;
    }

    public String getDateTime() {
        return dateTime;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void markSuccessful() {
        status = TransactionStatus.SUCCESSFUL;
    }

    public void markFailed() {
        status = TransactionStatus.FAILED;
    }
}
