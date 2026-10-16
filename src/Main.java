import data.DataStore;
import entities.Card;
import entities.Store;

import entities.transaction.Payment;

import entities.user.Student;
import entities.user.Vendor;

import entities.wallet.StudentWallet;
import entities.wallet.StoreWallet;

import services.PaymentService;

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

        Payment payment = PaymentService.processPayment(
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

        if (payment != null) {

            System.out.println("===== PAYMENT RESULT =====");

            System.out.println(
                "Payment ID: " +
                payment.getPaymentId()
            );

            System.out.println(
                "Amount: " +
                payment.getAmount()
            );

            System.out.println(
                "Status: " +
                payment.getStatus()
            );

            System.out.println(
                "Student: " +
                payment.getCard().getStudent().getFirstName()
            );

            System.out.println(
                "Vendor: " +
                payment.getVendor().getFirstName()
            );

            System.out.println(
                "Store: " +
                payment.getVendor().getStore().getStoreName()
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