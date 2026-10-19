package ui;

import java.util.Scanner;
import entities.user.Vendor;

import java.time.LocalDateTime;

import data.DataStore;
import entities.transaction.Payment;
import enums.TransactionStatus;
import services.PaymentService;

public class VendorMenu {

    public static void show(Vendor vendor, Scanner sc) {
        boolean running = true;

        while (running) {
            ConsoleUi.showSection("VENDOR MENU");

            ConsoleUi.showInfo("Vendor: " + vendor.getFirstName());
            ConsoleUi.showInfo("Store: " + vendor.getStore().getStoreName());
            ConsoleUi.showBalance("Store Wallet Balance", vendor.getStore().getStoreWallet().getBalance());

            System.out.println();
            ConsoleUi.showOption(1, "Process Payment");
            ConsoleUi.showExitOption("Logout");

            System.out.print("\nEnter Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    processPayment(vendor, sc);
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    ConsoleUi.showError("Invalid choice.");
                    pause(sc);
                    break;
            }
        }
    }

    private static void processPayment(Vendor vendor, Scanner sc) {
        ConsoleUi.showSection("PROCESS PAYMENT");

        System.out.print("Card UID: ");
        String cardUid = sc.nextLine();

        System.out.print("Amount: PHP ");
        double amount = sc.nextDouble();
        sc.nextLine();

        int paymentId = DataStore.generateNextPaymentId();
        String dateTime = LocalDateTime.now().toString();

        Payment payment = PaymentService.processPayment(paymentId, amount, dateTime, cardUid, vendor);

        if (payment == null) {
            ConsoleUi.showError("Payment could not be processed.");
            ConsoleUi.showInfo("Check the Card UID and payment amount.");
            pause(sc);
            return;
        }

        if (payment.getStatus() == TransactionStatus.FAILED) {
            ConsoleUi.showError("Payment failed.");
            ConsoleUi.showInfo("Check the student's balance or wallet status.");
            pause(sc);
            return;
        }

        ConsoleUi.showSuccess("Payment successful.");
        ConsoleUi.showInfo("Payment ID: " + payment.getPaymentId());
        ConsoleUi.showBalance("Amount", payment.getAmount());
        ConsoleUi.showBalance("Store Wallet Balance", vendor.getStore().getStoreWallet().getBalance());

        pause(sc);
    }

    private static void pause(Scanner sc) {
        System.out.println();
        System.out.print("Press ENTER to continue...");
        sc.nextLine();
    }
}