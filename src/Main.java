import datas.DataStore;

import entities.Account;
import entities.Store;

import entities.user.Student;
import entities.user.Vendor;
import entities.user.Cashier;
import entities.user.User;

import entities.wallet.StudentWallet;
import entities.wallet.StoreWallet;

import services.Authorization;

public class Main {

    public static void main(String[] args) {

        // =========================
        // STUDENT
        // =========================

        StudentWallet studentWallet = new StudentWallet(101);

        Student student = new Student(
            1,
            "Fernando",
            "V.",
            "Vidal",
            "09123456789",
            "fernando@email.com",
            "2025-001234",
            "BSIT",
            2,
            studentWallet
        );

        Account studentAccount = new Account(
            1001,
            "1234",
            student
        );


        // =========================
        // VENDOR
        // =========================

        StoreWallet storeWallet = new StoreWallet(201);

        Store store = new Store(
            1,
            "BulSU Canteen",
            "Food",
            "Main Campus",
            storeWallet
        );

        Vendor vendor = new Vendor(
            2,
            "Juan",
            "D.",
            "Santos",
            "09999999999",
            "juan@email.com",
            store
        );

        Account vendorAccount = new Account(
            1002,
            "5678",
            vendor
        );


        // =========================
        // CASHIER
        // =========================

        Cashier cashier = new Cashier(
            3,
            "Maria",
            "A.",
            "Cruz",
            "09888888888",
            "maria@email.com"
        );

        Account cashierAccount = new Account(
            1003,
            "9999",
            cashier
        );


        // =========================
        // ADD ACCOUNTS TO DATASTORE
        // =========================

        DataStore.addAccount(studentAccount);
        DataStore.addAccount(vendorAccount);
        DataStore.addAccount(cashierAccount);


        // =========================
        // TEST LOGIN
        // =========================

        testLogin(1001, "1234");
        testLogin(1002, "5678");
        testLogin(1003, "9999");
    }


    public static void testLogin(int accountId, String pin) {

        Account loggedInAccount =
            Authorization.login(accountId, pin);

        if (loggedInAccount != null) {

            User user = loggedInAccount.getUser();

            System.out.println("--------------------");
            System.out.println("Login successful!");
            System.out.println(
                "Welcome, " + user.getFirstName()
            );

            System.out.println(
                "User Type: " + user.getUserType()
            );

            String userType = user.getUserType();

            if (userType.equals("STUDENT")) {

                System.out.println("Opening Student Menu...");

            } else if (userType.equals("VENDOR")) {

                System.out.println("Opening Vendor Menu...");

            } else if (userType.equals("CASHIER")) {

                System.out.println("Opening Cashier Menu...");
            }

        } else {

            System.out.println("--------------------");
            System.out.println("Login failed.");
        }
    }
}