import datas.DataStore;

import entities.Account;
import entities.Store;

import entities.user.Student;
import entities.user.Vendor;
import entities.user.Cashier;

import entities.wallet.StudentWallet;
import entities.wallet.StoreWallet;

import services.Authorization;
import services.Registration;

public class Main {

    public static void main(String[] args) {

        // =========================
        // AUTHORIZED STUDENT
        // =========================

        StudentWallet studentWallet =
            new StudentWallet(101);

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

        DataStore.addAuthorizedStudent(student);


        // =========================
        // AUTHORIZED VENDOR
        // =========================

        StoreWallet storeWallet =
            new StoreWallet(201);

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

        DataStore.addAuthorizedVendor(vendor);


        // =========================
        // AUTHORIZED CASHIER
        // =========================

        Cashier cashier = new Cashier(
            3,
            "Maria",
            "A.",
            "Cruz",
            "09888888888",
            "maria@email.com"
        );

        DataStore.addAuthorizedCashier(cashier);


        // =========================
        // REGISTER
        // =========================

        Account studentAccount =
            Registration.registerStudent(
                1001,
                "1234",
                "2025-001234"
            );

        Account vendorAccount =
            Registration.registerVendor(
                1002,
                "5678",
                2
            );

        Account cashierAccount =
            Registration.registerCashier(
                1003,
                "9999",
                3
            );


        // =========================
        // RESULTS
        // =========================

        if (studentAccount != null) {
            System.out.println("Student registered successfully.");
        }

        if (vendorAccount != null) {
            System.out.println("Vendor registered successfully.");
        }

        if (cashierAccount != null) {
            System.out.println("Cashier registered successfully.");
        }


        // =========================
        // LOGIN AFTER REGISTRATION
        // =========================

        Account loggedInAccount =
            Authorization.login(1002, "5678");

        if (loggedInAccount != null) {

            System.out.println();
            System.out.println("Login successful.");
            System.out.println(
                "Welcome, " +
                loggedInAccount.getUser().getFirstName()
            );

            System.out.println(
                "User Type: " +
                loggedInAccount.getUser().getUserType()
            );
        }
    }
}