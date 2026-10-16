package services;

import datas.DataStore;

import entities.transaction.CashOut;

import entities.user.Cashier;
import entities.user.Vendor;

import entities.wallet.StoreWallet;

public class CashOutService {

    public static CashOut processCashOut(int cashOutId, double amount, String dateTime, Vendor vendor, Cashier cashier) {

        // Invalid amount
        if (amount <= 0) {
            return null;
        }

        // CashOut ID must be unique
        if (DataStore.findCashOutById(cashOutId) != null) {
            return null;
        }

        // Vendor and Cashier must exist
        if (vendor == null || cashier == null) {
            return null;
        }

        // Vendor must have a Store
        if (vendor.getStore() == null) {
            return null;
        }

        // Get Store Wallet
        StoreWallet storeWallet = vendor.getStore().getStoreWallet();

        if (storeWallet == null) {
            return null;
        }

        // Create CashOut record
        CashOut cashOut = new CashOut(cashOutId, amount, dateTime, cashier, vendor, storeWallet);

        // Wallet must be active
        if (!storeWallet.isActive()) {
            cashOut.markFailed();
            DataStore.addCashOut(cashOut);

            return cashOut;
        }

        // Deduct funds
        boolean deducted = storeWallet.deductBalance(amount);

        if (!deducted) {
            cashOut.markFailed();
            DataStore.addCashOut(cashOut);

            return cashOut;
        }

        cashOut.markSuccessful();

        DataStore.addCashOut(cashOut);

        return cashOut;
    }
}