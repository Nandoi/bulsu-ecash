package entities.transaction;

import entities.Card;
import entities.user.Vendor;
import entities.wallet.StudentWallet;
import entities.wallet.StoreWallet; 

public class Payment extends TransactionRecord {
    private int paymentId;

    //Related objects na gagamitin
    private Card card;
    private StudentWallet studentWallet;
    private Vendor vendor;
    private StoreWallet storeWallet;

    public Payment(int paymentId,
        double amount,
        String dateTime,
        Card card,
        StudentWallet studentWallet,
        Vendor vendor,
        StoreWallet storeWallet
    ){
        super(amount, dateTime);

        this.paymentId = paymentId;
        this.card = card;
        this.studentWallet = studentWallet;
        this.vendor = vendor;
        this.storeWallet = storeWallet;
    }

    public int getPaymentId() {
        return paymentId;
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

