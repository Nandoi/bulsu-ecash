package services;

import data.DataStore;
import entities.Account;
import entities.user.Cashier;
import entities.user.Student;
import entities.user.User;
import entities.user.Vendor;

public class RegistrationService {
    // STUDENT
    public static Account registerStudent(String pin, String studentNumber){
        Student student = DataStore.findStudentByStudentNumber(studentNumber);

        if (student == null){
            return null;
        }

        return createAccount(pin, student);
    }


    // VENDOR
    public static Account registerVendor(String pin,int userId){
        Vendor vendor = DataStore.findVendorByUserId(userId);

        if (vendor == null) {
            return null;
        }

        return createAccount(pin, vendor);
    }


    // CASHIER
    public static Account registerCashier(String pin, int userId) {
        Cashier cashier = DataStore.findCashierByUserId(userId);

        if (cashier == null) {
            return null;
        }

        return createAccount(pin, cashier);
    }


    // ACCOUNT CREATION
    private static Account createAccount(String pin, User user) {
        // Dito titignan if may account naba yung user.
        if (DataStore.findAccountByUserId(user.getUserId()) != null) {
            return null;
        }

        int accountId  = DataStore.generateNextAccountId();

        Account account = new Account(accountId, pin, user);

        DataStore.addAccount(account);

        return account;
    }
}
