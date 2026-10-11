package services;

import datas.DataStore;
import entities.Account;
import entities.user.Cashier;
import entities.user.Student;
import entities.user.User;
import entities.user.Vendor;

public class Registration {
    // STUDENT
    public static Account registerStudent(int accountId, String pin, String studentNumber){
        Student student = DataStore.findStudentByStudentNumber(studentNumber);

        if (student == null){
            return null;
        }

        return createAccount(accountId, pin, student);
    }


    // VENDOR
    public static Account registerVendor(int accountId,String pin,int userId){
        Vendor vendor = DataStore.findVendorByUserId(userId);

        if (vendor == null) {
            return null;
        }

        return createAccount(accountId, pin, vendor);
    }


    // CASHIER
    public static Account registerCashier(int accountId, String pin, int userId) {
        Cashier cashier = DataStore.findCashierByUserId(userId);

        if (cashier == null) {
            return null;
        }

        return createAccount(accountId, pin, cashier);
    }


    // ACCOUNT CREATION
    private static Account createAccount(
        int accountId,
        String pin,
        User user
    ) {
        // Dito titignan if existing naba ang account.
        if (DataStore.findAccountById(accountId) != null) {
            return null;
        }

        // Dito titignan if may account naba yung user.
        if (DataStore.findAccountByUserId(user.getUserId()) != null) {
            return null;
        }

        Account account = new Account(accountId, pin, user);

        DataStore.addAccount(account);

        return account;
    }
}
