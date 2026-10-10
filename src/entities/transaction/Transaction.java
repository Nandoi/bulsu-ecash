package entities.transaction;

import entities.Card;
import entities.user.Vendor;
import entities.wallet.StudentWallet;
import entities.wallet.StoreWallet; 

public class Transaction extends TransactionRecord {
    private int transactionId;

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
        super(amount, dateTime);

        this.transactionId = transactionId;
        this.card = card;
        this.studentWallet = studentWallet;
        this.vendor = vendor;
        this.storeWallet = storeWallet;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public Card getCard() {
        return card;
    }

    public StudentWallet getStudentWallet() {
        return studentWallet;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public StoreWallet getStoreWallet() {
        return storeWallet;
    }
}

