package services;

import data.DataStore;
import entities.Account;
import entities.user.Cashier;
import entities.user.Student;
import entities.user.User;
import entities.user.Vendor;
import entities.Card;
import entities.wallet.StudentWallet;
import entities.Store;
import entities.wallet.StoreWallet;

public class RegistrationService {
    // STUDENT
    public static Account registerStudent(String pin, String studentNumber) {
        Student student = DataStore.findStudentByStudentNumber(studentNumber);

        if (student == null) {
            return null;
        }

        if (DataStore.findAccountByUserId(student.getUserId()) != null) {
            return null;
        }

        if (student.getStudentWallet() != null) {
            return null;
        }

        StudentWallet studentWallet = new StudentWallet(DataStore.generateNextStudentWalletId());

        if (!student.assignStudentWallet(studentWallet)) {
            return null;
        }

        Account account = new Account(DataStore.generateNextAccountId(), pin, student);

        DataStore.addAccount(account);

        Card card = new Card(DataStore.generateNextCardUid(), student);

        DataStore.addCard(card);

        return account;
    }


    // VENDOR
    public static Account registerVendor(
            String pin,
            int userId,
            String storeName,
            String storeType,
            String location) {

        // Find authorized Vendor
        Vendor vendor = DataStore.findVendorByUserId(userId);

        if (vendor == null) {
            return null;
        }

        // Prevent duplicate registration
        if (DataStore.findAccountByUserId(userId) != null) {
            return null;
        }

        // Prevent duplicate Store assignment
        if (vendor.getStore() != null) {
            return null;
        }

        // Validate Store information
        if (storeName == null || storeType == null || location == null
                || storeName.trim().isEmpty()
                || storeType.trim().isEmpty()
                || location.trim().isEmpty()) {
            return null;
        }

        // Create Store Wallet
        StoreWallet storeWallet = new StoreWallet(
                DataStore.generateNextStoreWalletId()
        );

        // Create Store linked to its Wallet
        Store store = new Store(
                DataStore.generateNextStoreId(),
                storeName.trim(),
                storeType.trim(),
                location.trim(),
                storeWallet
        );

        // Assign Store to Vendor
        if (!vendor.assignStore(store)) {
            return null;
        }

        // Create account only after validation
        Account account = new Account(
                DataStore.generateNextAccountId(),
                pin,
                vendor
        );

        DataStore.addAccount(account);

        return account;
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
