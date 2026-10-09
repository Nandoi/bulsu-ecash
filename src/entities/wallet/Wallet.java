package entities.wallet;

public abstract class Wallet {
    private int walletId;
    private double balance;

    protected Wallet(int walletId){
        this.walletId = walletId;
        this.balance = 0.0;
    }

    public int getWalletId(){
        return walletId;
    }

    public double getBalance(){
        return balance;
    }

    public boolean credit(double amount) {
        if (amount > 0){
            balance += amount;
            return true;
        }

        return false;
    }

    public boolean debit(double amount) {
        if (amount > 0 && balance >= amount){
            balance -= amount;
            return true;
        }

        return false;
    }
}
