package entities.transaction;

import entities.user.Cashier;
import entities.user.Vendor;
import entities.wallet.StoreWallet;
public class CashOut extends TransactionRecord {
    private int cashOutId;

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
        super(amount, dateTime);
        this.cashOutId = cashOutId;
        this.cashier = cashier;
        this.vendor = vendor;
        this.storeWallet = storeWallet;
    }

    public int getCashOutId() {
        return cashOutId;
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
}
