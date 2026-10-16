package services;

import data.DataStore;
import entities.Account;

public class AuthenticationService {
    public static Account login(int accountId, String pin) {
        Account account = DataStore.findAccountById(accountId);

        if (account == null){
            return null;

        } else if (!account.isActive()){
            return null;
            
        } else if (!account.verifyPin(pin)){
            return null;

        } else {
            return account;
        }
    }
}
