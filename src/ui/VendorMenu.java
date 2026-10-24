package ui;

import java.util.Scanner;

import data.DataStore;
import entities.user.Vendor;
import entities.transaction.CashOut;
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
            ConsoleUi.showOption(2, "View Payment History");
            ConsoleUi.showOption(3, "View Cash Out History");
            ConsoleUi.showExitOption("Logout");

            System.out.print("\nEnter Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    processPayment(vendor, sc);
                    break;

                case 2:
                    showPaymentHistory(vendor);
                    ConsoleUi.pause(sc);
                    break;

                case 3:
                    showCashOutHistory(vendor);
                    ConsoleUi.pause(sc);
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    ConsoleUi.showError("Invalid choice.");
                    ConsoleUi.pause(sc);
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

        Payment payment = PaymentService.processPayment(amount, cardUid, vendor);

        if (payment == null) {
            ConsoleUi.showError("Payment could not be processed.");
            ConsoleUi.showInfo("Check the Card UID and payment amount.");
            ConsoleUi.pause(sc);
            return;
        }

        if (payment.getStatus() == TransactionStatus.FAILED) {
            ConsoleUi.showError("Payment failed.");
            ConsoleUi.showInfo("Check the student's balance or wallet status.");
            ConsoleUi.pause(sc);
            return;
        }

        ConsoleUi.showSuccess("Payment successful.");
        ConsoleUi.showInfo("Payment ID: " + payment.getPaymentId());
        ConsoleUi.showBalance("Amount", payment.getAmount());
        ConsoleUi.showBalance("Store Wallet Balance", vendor.getStore().getStoreWallet().getBalance());

        ConsoleUi.showReceipt(
            payment.getTransactionType(),
            payment.getPaymentId(),
            payment.getAmount(),
            payment.getDateTime(),
            "Student Wallet (Card " + payment.getCard().getCardUid() + ")",
            vendor.getStore().getStoreName()
        );

        ConsoleUi.pause(sc);
    }

    private static void showPaymentHistory(Vendor vendor) {
        ConsoleUi.showSection("PAYMENT HISTORY");

        boolean found = false;

        for (Payment payment : DataStore.getPayments()) {
            if (payment.getVendor() == vendor) {
                System.out.println("Payment ID: " + payment.getPaymentId());
                System.out.println("Amount: PHP " + payment.getAmount());
                System.out.println("Date: " + payment.getDateTime());
                System.out.println("Status: " + payment.getStatus());
                System.out.println("----------------------------------------");

                found = true;
            }
        }

        if (!found) {
            ConsoleUi.showInfo("No payment records found.");
        }
    }

    private static void showCashOutHistory(Vendor vendor) {
        ConsoleUi.showSection("CASH OUT HISTORY");

        boolean found = false;

        for (CashOut cashOut : DataStore.getCashOuts()) {
            if (cashOut.getVendor() == vendor) {
                System.out.println("Cash Out ID: " + cashOut.getCashOutId());
                System.out.println("Type: " + cashOut.getTransactionType());
                System.out.println("Amount: PHP " + cashOut.getAmount());
                System.out.println("Date: " + cashOut.getDateTime());
                System.out.println("Status: " + cashOut.getStatus());
                System.out.println("----------------------------------------");

                found = true;
            }
        }

        if (!found) {
            ConsoleUi.showInfo("No cash out records found.");
        }
    }
}
