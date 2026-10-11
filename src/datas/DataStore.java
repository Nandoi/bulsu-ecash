package datas;

import java.util.ArrayList;

import entities.Account;

public class DataStore {
    private static ArrayList<Account> accounts = new ArrayList<>();

    public static void addAccount(Account account){
        accounts.add(account);
    }

    public static Account findAccountById(int accountId){

        for (Account acc : accounts){

            if (acc.getAccountId() == accountId){
                return acc;
            }
        }

        return null;
    }
}
