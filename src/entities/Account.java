package entities;

import entities.user.User;
import enums.AccountStatus;

public class Account {
    private int accountId;
    private String pin;
    private AccountStatus status;
    private User user;

    public Account(int accountId, String pin, User user){
        this.accountId = accountId;
        this.pin = pin;
        this.user = user;
        this.status = AccountStatus.ACTIVE;
    }

    public int getAccountId(){
        return accountId;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public boolean verifyPin(String enteredPin) {
        return pin.equals(enteredPin);
    }

    public boolean isActive() {
        return status == AccountStatus.ACTIVE;
    }
}
