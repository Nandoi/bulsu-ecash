package entities.transaction;

import entities.user.Cashier;
import entities.wallet.StudentWallet;
import enums.TransactionStatus;
public class TopUp{
    private int topUpId;
    private double amount;
    private String dateTime;
    private TransactionStatus status;

    //Related objects na gagamitin
    private Cashier cashier;
    private StudentWallet studentWallet;

    public TopUp(int topUpId,
        double amount,
        String dateTime,
        Cashier cashier,
        StudentWallet studentWallet
    ){
        this.topUpId = topUpId;
        this.amount = amount;
        this.dateTime = dateTime;
        this.cashier = cashier;
        this.studentWallet = studentWallet;
        this.status = TransactionStatus.PENDING;
    }

    public int getTopUpId() {
        return topUpId;
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

    public Cashier getCashier() {
        return cashier;
    }

    public StudentWallet getStudentWallet() {
        return studentWallet;
    }

    public void markSuccessful() {
        status = TransactionStatus.SUCCESSFUL;
    }

    public void markFailed() {
        status = TransactionStatus.FAILED;
    }

}