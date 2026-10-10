package entities.transaction;

import entities.Card;
import entities.user.Vendor;
import entities.wallet.StudentWallet;
import entities.wallet.StoreWallet;
import enums.TransactionStatus;

public class Transaction {
    private int transactionId;
    private double amount;
    private String dateTime;
    private TransactionStatus status;

    //Related objects na gagamitin
    private Card card;
    private StudentWallet studentWallet;
    private Vendor vendor;
    private StoreWallet storeWallet;

    public Transaction(int transactionId,
        double amount,
        String dateTime,
        Card card,
        StudentWallet studentWallet,
        Vendor vendor,
        StoreWallet storeWallet
    ){
        this.transactionId = transactionId;
        this.amount = amount;
        this.dateTime = dateTime;
        this.card = card;
        this.studentWallet = studentWallet;
        this.vendor = vendor;
        this.storeWallet = storeWallet;
        this.status = TransactionStatus.PENDING;
    }

    public int getTransactionId(){
        return transactionId;
    }

    public double getAmount(){
        return amount;
    }
    
    public String getDateTime(){
        return dateTime;
    }

    public TransactionStatus getStatus(){
        return status;
    }

    public Card getCard(){
        return card;
    }

    public StudentWallet getStudentWallet(){
        return studentWallet;
    }

    public Vendor getVendor(){
        return vendor;
    }

    public StoreWallet getStoreWallet() {
        return storeWallet;
    }

    public void markSuccessful() {
        status = TransactionStatus.SUCCESSFUL;
    }

    public void markFailed() {
        status = TransactionStatus.FAILED;
    }
}

