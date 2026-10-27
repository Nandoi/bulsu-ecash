package ui;

import java.util.Scanner;

import data.DataStore;
import entities.transaction.CashOut;
import entities.transaction.Payment;
import entities.user.Student;
import entities.user.Vendor;
import entities.Card;
import enums.TransactionStatus;
import services.PaymentService;

public class VendorMenu {

    public static void show(Vendor vendor, Scanner sc) {
        boolean running = true;

        while (running) {
            showMenu(vendor);
            int choice = ConsoleUi.readInt(sc, "\nEnter Choice: ");

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

                case 4:
                    showSalesSummary(vendor);
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

        //Ask for card UID and validation
        System.out.print("Card UID: ");
        String cardUid = sc.nextLine();

        Card card = DataStore.findCardByUid(cardUid);

        if (card == null) {
            ConsoleUi.showError("Card not found.");
            ConsoleUi.pause(sc);
            return;
        }

        if (!card.isActive()) {
            ConsoleUi.showError("Card is not active.");
            ConsoleUi.showInfo("Card Status: " + card.getStatus());
            ConsoleUi.pause(sc);
            return;
        }

        Student student = card.getStudent();

        //Ask for amount and validation
        double amount = ConsoleUi.readDouble(sc, "Amount: PHP ");

        if (amount <= 0){
            ConsoleUi.showError("Amount must be greater than 0.");
            ConsoleUi.pause(sc);
            return;
        }

        //Ask for confirmation and display transaction details
        showPaymentConfirmation(student, card, vendor, amount);

        //A boolean method to confirm the transaction, if not confirmed, return to the menu
        if (!ConsoleUi.confirmTransaction(sc)) {
            ConsoleUi.showInfo("Payment cancelled.");
            ConsoleUi.pause(sc);
            return;
        }

        //If boolean method returns true, process the payment
        Payment payment = PaymentService.processPayment(amount, cardUid, vendor);

        //If payment service returns null, display error message and return to the menu
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

        //If payment is successful, display success message
        showPaymentReceipt(payment);

        ConsoleUi.pause(sc);
    }


    private static void showPaymentConfirmation(Student student, Card card, Vendor vendor, double amount) {
        ConsoleUi.boxHeader("PAYMENT CONFIRMATION");

        // Student information
        ConsoleUi.boxLine("  Student: " + student.getFirstName() + " " + student.getLastName());
        ConsoleUi.boxLine("  Student No.: " + student.getStudentNumber());
        ConsoleUi.boxLine("  Card UID: " + card.getCardUid());
        ConsoleUi.boxDivider();

        // Payment information
        ConsoleUi.boxLine("  Store: " + vendor.getStore().getStoreName());
        ConsoleUi.boxLine("  Handled By: " + vendor.getFirstName() + " " + vendor.getLastName());
        ConsoleUi.boxLine("");
        ConsoleUi.boxLine("  AMOUNT TO PAY: " + String.format("PHP %,.2f", amount));
        ConsoleUi.boxFooter();
    }

    private static void showPaymentReceipt(Payment payment) {
        Student student = payment.getCard().getStudent();
        Vendor vendor = payment.getVendor();

        ConsoleUi.boxHeader("PAYMENT RECEIPT");

        // Transaction details
        ConsoleUi.boxLine("  Status: " + payment.getStatus());
        ConsoleUi.boxLine("  Transaction ID: " + payment.getPaymentId());
        ConsoleUi.boxLine("  Date: "  + ConsoleUi.formatDateTime(payment.getDateTime()));
        ConsoleUi.boxDivider();

        // Student and store information
        ConsoleUi.boxLine("  Student: " + student.getFirstName() + " " + student.getLastName());
        ConsoleUi.boxLine("  Store: " + vendor.getStore().getStoreName());
        ConsoleUi.boxLine("  Card UID: " + payment.getCard().getCardUid());
        ConsoleUi.boxDivider();

        // Final payment details
        ConsoleUi.boxLine("  AMOUNT RECEIVED: " + String.format("PHP %,.2f", payment.getAmount()));
        ConsoleUi.boxLine("  Store Balance: " + String.format("PHP %,.2f", payment.getStoreWallet().getBalance()));
        ConsoleUi.boxFooter();
    }

    private static void showPaymentHistory(Vendor vendor) {
        ConsoleUi.boxHeader("PAYMENT HISTORY");

        ConsoleUi.boxLine("  Store: " + vendor.getStore().getStoreName());
        ConsoleUi.boxDivider();

        boolean found = false;

        for (Payment payment : DataStore.getPayments()) {
            if (payment.getVendor() == vendor) {

                if (found) {
                    ConsoleUi.boxRecordDivider();
                }

                showPaymentRecord(payment);
                found = true;
            }
        }

        if (!found) {
            ConsoleUi.boxLine("  No payment records found.");
        }

        ConsoleUi.boxFooter();
    }


    private static void showPaymentRecord(Payment payment) {
        Student student = payment.getCard().getStudent();

        ConsoleUi.boxLine("  #" + payment.getPaymentId()
                + " | " + payment.getStatus());

        ConsoleUi.boxLine("  Date: "
                + ConsoleUi.formatDateTime(payment.getDateTime()));

        ConsoleUi.boxLine("  Student: "
                + student.getFirstName() + " " + student.getLastName());

        ConsoleUi.boxLine("  Student No.: "
                + student.getStudentNumber());

        ConsoleUi.boxLine("  Card UID: "
                + payment.getCard().getCardUid());

        if (payment.getStatus() == TransactionStatus.SUCCESSFUL) {
            ConsoleUi.boxLine("  RECEIVED: " + String.format("PHP %,.2f", payment.getAmount()));
        } else {
            ConsoleUi.boxLine("  ATTEMPTED: " + String.format("PHP %,.2f", payment.getAmount()));
        }
    }

    private static void showCashOutHistory(Vendor vendor) {
        ConsoleUi.boxHeader("CASH-OUT HISTORY");

        ConsoleUi.boxLine("  Store: " + vendor.getStore().getStoreName());
        ConsoleUi.boxDivider();

        boolean found = false;

        for (CashOut cashOut : DataStore.getCashOuts()) {
            if (cashOut.getVendor() == vendor) {

                if (found) {
                    ConsoleUi.boxRecordDivider();
                }

                showCashOutRecord(cashOut);
                found = true;
            }
        }

        if (!found) {
            ConsoleUi.boxLine("  No cash-out records found.");
        }

        ConsoleUi.boxFooter();
    }

    private static void showCashOutRecord(CashOut cashOut) {
        ConsoleUi.boxLine("  #" + cashOut.getCashOutId() + " | " + cashOut.getStatus());
        ConsoleUi.boxLine("  Date: " + ConsoleUi.formatDateTime(cashOut.getDateTime()));
        ConsoleUi.boxLine("  Cashier: " + cashOut.getCashier().getFirstName() + " " + cashOut.getCashier().getLastName());

        if (cashOut.getStatus() == TransactionStatus.SUCCESSFUL) {
            ConsoleUi.boxLine("  WITHDRAWN: " + String.format("PHP %,.2f", cashOut.getAmount()));
        } else {
            ConsoleUi.boxLine("  ATTEMPTED: " + String.format("PHP %,.2f", cashOut.getAmount()));
        }
    }

    private static void showSalesSummary(Vendor vendor) {
        int successfulPayments = 0;
        int failedPayments = 0;
        double totalSales = 0;

        int successfulCashOuts = 0;
        int failedCashOuts = 0;
        double totalWithdrawn = 0;

        // Count payment transactions
        for (Payment payment : DataStore.getPayments()) {
            if (payment.getVendor() == vendor) {

                if (payment.getStatus() == TransactionStatus.SUCCESSFUL) {
                    successfulPayments++;
                    totalSales += payment.getAmount();

                } else if (payment.getStatus() == TransactionStatus.FAILED) {
                    failedPayments++;
                }
            }
        }

        // Count cash-out transactions
        for (CashOut cashOut : DataStore.getCashOuts()) {
            if (cashOut.getVendor() == vendor) {

                if (cashOut.getStatus() == TransactionStatus.SUCCESSFUL) {
                    successfulCashOuts++;
                    totalWithdrawn += cashOut.getAmount();

                } else if (cashOut.getStatus() == TransactionStatus.FAILED) {
                    failedCashOuts++;
                }
            }
        }

        int totalTransactions = successfulPayments + failedPayments + successfulCashOuts + failedCashOuts;

        // Sales summary UI
        ConsoleUi.boxHeader("VENDOR SALES SUMMARY");
        ConsoleUi.boxLine("  Store: " + vendor.getStore().getStoreName());
        ConsoleUi.boxLine("  Vendor: " + vendor.getFirstName() + " " + vendor.getLastName());
        ConsoleUi.boxDivider();

        // Payment summary
        ConsoleUi.boxLine("  PAYMENT SUMMARY");
        ConsoleUi.boxLine("  Successful: " + successfulPayments + "  |  Failed: " + failedPayments);
        ConsoleUi.boxLine("  Total Sales: " + String.format("PHP %,.2f", totalSales));
        ConsoleUi.boxDivider();

        // Cash-out summary
        ConsoleUi.boxLine("  CASH-OUT SUMMARY");
        ConsoleUi.boxLine("  Successful: " + successfulCashOuts + "  |  Failed: " + failedCashOuts);
        ConsoleUi.boxLine("  Total Withdrawn: " + String.format("PHP %,.2f", totalWithdrawn));
        ConsoleUi.boxDivider();

        // Overall summary
        ConsoleUi.boxLine("  OVERALL SUMMARY");
        ConsoleUi.boxLine("  Total Transactions: " + totalTransactions);
        ConsoleUi.boxLine("  Current Store Balance: " + String.format("PHP %,.2f", vendor.getStore().getStoreWallet().getBalance()));
        ConsoleUi.boxLine("  Current session records only");
        ConsoleUi.boxFooter();
    }

    private static void showMenu(Vendor vendor) {
        String storeName = vendor.getStore().getStoreName();
        String balance = String.format("PHP %,.2f", vendor.getStore().getStoreWallet().getBalance());

        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "                  VENDOR DASHBOARD");
        System.out.println("├────────────────────────────────────────────────────┤");

        // Vendor information
        System.out.printf("│ %-50.50s │%n",
                "  " + "Hello, " + vendor.getFirstName() + " " + vendor.getLastName());
        System.out.printf("│ %-50.50s │%n", "  Store: " + storeName);

        System.out.println("├────────────────────────────────────────────────────┤");

        // Store wallet
        System.out.printf("│ %-50s │%n", "  STORE WALLET");
        System.out.printf("│ %-50.50s │%n", "  " + balance);

        System.out.println("├────────────────────────────────────────────────────┤");

        // Menu options
        System.out.printf("│ %-50s │%n", "  [1] Process Payment");
        System.out.printf("│ %-50s │%n", "  [2] Payment History");
        System.out.printf("│ %-50s │%n", "  [3] Cash-Out History");
        System.out.printf("│ %-50s │%n", "  [4] Sales Summary");
        System.out.printf("│ %-50s │%n", "  [0] Logout");

        System.out.println("└────────────────────────────────────────────────────┘");
    }
}
