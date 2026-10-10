package entities.transaction;

import entities.user.Cashier;
import entities.user.Vendor;
import entities.wallet.StoreWallet;
import enums.TransactionStatus;

public class CashOut {
    private int cashOutId;
    private double amount;
    private String dateTime;
    private TransactionStatus status;

    //Related objects na gagamitin
    private Cashier cashier;
    private Vendor vendor;
    private StoreWallet storeWallet;

    public CashOut(int cashOutId,
        double amount,
        String dateTime,
        Cashier cashier,
        Vendor vendor,
        StoreWallet storeWallet
    ){
        this.cashOutId = cashOutId;
        this.amount = amount;
        this.dateTime = dateTime;
        this.cashier = cashier;
        this.vendor = vendor;
        this.storeWallet = storeWallet;
        this.status = TransactionStatus.PENDING;
    }

    public int getCashOutId() {
        return cashOutId;
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

    public Vendor getVendor() {
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
