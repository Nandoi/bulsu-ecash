package entities.wallet;

import enums.WalletStatus;

public abstract class Wallet {
    private int walletId;
    private double balance;
    private WalletStatus status;

    protected Wallet(int walletId){
        this.walletId = walletId;
        this.balance = 0.0;
        this.status = WalletStatus.ACTIVE;
    }

    public int getWalletId(){
        return walletId;
    }

    public double getBalance(){
        return balance;
    }

    public WalletStatus getStatus(){
        return status;
    }

    public boolean addBalance(double amount) {
        if (status == WalletStatus.ACTIVE && amount > 0){
            balance += amount;
            return true;
        }
        return false;
    }
    
    public boolean deductBalance(double amount) {
        if (status == WalletStatus.ACTIVE && amount > 0 && balance >= amount){
            balance -= amount;
            return true;
        }
        return false;
    }
}
