import datas.DataStore;

import entities.Card;
import entities.Store;

import entities.transaction.Transaction;

import entities.user.Student;
import entities.user.Vendor;

import entities.wallet.StudentWallet;
import entities.wallet.StoreWallet;

import services.Payment;

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


        // =========================
        // STUDENT CARD
        // =========================

        Card card = new Card(
            301,
            "CARD-001",
            student
        );

        DataStore.addCard(card);


        // =========================
        // STORE
        // =========================

        StoreWallet storeWallet = new StoreWallet(201);

        Store store = new Store(
            1,
            "BulSU Canteen",
            "Food",
            "Main Campus",
            storeWallet
        );


        // =========================
        // VENDOR
        // =========================

        Vendor vendor = new Vendor(
            2,
            "Juan",
            "D.",
            "Santos",
            "09999999999",
            "juan@email.com",
            store
        );


        // =========================
        // STUDENT INITIAL BALANCE
        // =========================

        studentWallet.addBalance(500.00);


        // =========================
        // BEFORE PAYMENT
        // =========================

        System.out.println("===== BEFORE PAYMENT =====");

        System.out.println(
            "Student Balance: " +
            studentWallet.getBalance()
        );

        System.out.println(
            "Store Balance: " +
            storeWallet.getBalance()
        );


        // =========================
        // PAYMENT
        // =========================

        System.out.println();
        System.out.println("===== PROCESSING PAYMENT =====");

        Transaction transaction = Payment.processPayment(
            5001,
            150.00,
            "2026-10-11 20:30",
            "CARD-001",
            vendor
        );


        // =========================
        // PAYMENT RESULT
        // =========================

        System.out.println();

        if (transaction != null) {

            System.out.println("===== TRANSACTION RESULT =====");

            System.out.println(
                "Transaction ID: " +
                transaction.getTransactionId()
            );

            System.out.println(
                "Amount: " +
                transaction.getAmount()
            );

            System.out.println(
                "Status: " +
                transaction.getStatus()
            );

            System.out.println(
                "Student: " +
                transaction.getCard().getStudent().getFirstName()
            );

            System.out.println(
                "Vendor: " +
                transaction.getVendor().getFirstName()
            );

            System.out.println(
                "Store: " +
                transaction.getVendor().getStore().getStoreName()
            );

        } else {

            System.out.println("Payment could not be processed.");
        }


        // =========================
        // AFTER PAYMENT
        // =========================

        System.out.println();
        System.out.println("===== AFTER PAYMENT =====");

        System.out.println(
            "Student Balance: " +
            studentWallet.getBalance()
        );

        System.out.println(
            "Store Balance: " +
            storeWallet.getBalance()
        );
    }
}