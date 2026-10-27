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
            String storeName = vendor.getStore().getStoreName();
            String balance = String.format("PHP %,.2f", vendor.getStore().getStoreWallet().getBalance());

            System.out.println();
            System.out.println("┌────────────────────────────────────────────────────┐");
            System.out.printf("│ %-50s │%n", "                  VENDOR DASHBOARD");
            System.out.println("├────────────────────────────────────────────────────┤");

            System.out.printf("│ %-50s │%n", "");
            System.out.printf("│ %-50.50s │%n", "  Welcome, " + vendor.getFirstName() + "!");
            System.out.printf("│ %-50s │%n", "");

            System.out.printf("│ %-50s │%n", "  STORE OVERVIEW");
            System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));
            System.out.printf("│ %-50.50s │%n", "  Store Name: " + storeName);
            System.out.printf("│ %-50.50s │%n", "  Store Wallet: " + balance);
            System.out.printf("│ %-50s │%n", "");

            System.out.printf("│ %-50s │%n", "  STORE SERVICES");
            System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));
            System.out.printf("│ %-50s │%n", "");

            System.out.printf("│ %-50s │%n", "  [1] Process Payment");
            System.out.printf("│ %-50s │%n", "  [2] View Payment History");
            System.out.printf("│ %-50s │%n", "  [3] View Cash Out History");
            System.out.printf("│ %-50s │%n", "  [0] Logout");
            System.out.printf("│ %-50s │%n", "");

            System.out.println("└────────────────────────────────────────────────────┘");


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
        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "                PAYMENT CONFIRMATION");
        System.out.println("├────────────────────────────────────────────────────┤");

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  STUDENT INFORMATION");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50.50s │%n", "  Student: " + student.getFirstName() + " " + student.getLastName());
        System.out.printf("│ %-50.50s │%n", "  Student Number: " + student.getStudentNumber());
        System.out.printf("│ %-50.50s │%n", "  Card UID: " + card.getCardUid());

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  PAYMENT DETAILS");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50.50s │%n", "  Store: " + vendor.getStore().getStoreName());
        System.out.printf("│ %-50.50s │%n", "  Handled By: " + vendor.getFirstName() + " " + vendor.getLastName());

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50.50s │%n", "  TOTAL AMOUNT: " + String.format("PHP %,.2f", amount));
        System.out.printf("│ %-50s │%n", "");

        System.out.println("└────────────────────────────────────────────────────┘");

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
        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "                    BULSU ECASH");
        System.out.printf("│ %-50s │%n", "                   PAYMENT RECEIPT");
        System.out.println("├────────────────────────────────────────────────────┤");

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50.50s │%n", "  Transaction ID: " + payment.getPaymentId());
        System.out.printf("│ %-50.50s │%n", "  Date: " + ConsoleUi.formatDateTime(payment.getDateTime()));
        System.out.printf("│ %-50.50s │%n", "  Status: " + payment.getStatus());

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  TRANSACTION DETAILS");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50.50s │%n", "  Student: " + student.getFirstName() + " " + student.getLastName());
        System.out.printf("│ %-50.50s │%n", "  Store: " + vendor.getStore().getStoreName());
        System.out.printf("│ %-50.50s │%n", "  Card UID: " + card.getCardUid());

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));
        System.out.printf("│ %-50.50s │%n", "  TOTAL PAID: " + String.format("PHP %,.2f", payment.getAmount()));
        System.out.printf("│ %-50.50s │%n", "  Store Balance: " + String.format("PHP %,.2f", vendor.getStore().getStoreWallet().getBalance()));
        System.out.printf("│ %-50s │%n", "");

        System.out.println("└────────────────────────────────────────────────────┘");

        ConsoleUi.pause(sc);
    }


    private static void showPaymentHistory(Vendor vendor) {
        ConsoleUi.showSection("PAYMENT HISTORY");

        boolean found = false;

        for (Payment payment : DataStore.getPayments()) {
            if (payment.getVendor() == vendor) {
                Student student = payment.getCard().getStudent();

                System.out.println();
                System.out.println("┌────────────────────────────────────────────────────┐");
                System.out.printf("│ %-50s │%n", "                  PAYMENT HISTORY");
                System.out.println("├────────────────────────────────────────────────────┤");

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50.50s │%n", "  Transaction ID: " + payment.getPaymentId());
                System.out.printf("│ %-50.50s │%n", "  Date: " + ConsoleUi.formatDateTime(payment.getDateTime()));
                System.out.printf("│ %-50.50s │%n", "  Status: " + payment.getStatus());

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50s │%n", "  CUSTOMER INFORMATION");
                System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));
                System.out.printf("│ %-50.50s │%n", "  Student: " + student.getFirstName() + " " + student.getLastName());
                System.out.printf("│ %-50.50s │%n", "  Student Number: " + student.getStudentNumber());
                System.out.printf("│ %-50.50s │%n", "  Card UID: " + payment.getCard().getCardUid());

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50s │%n", "  PAYMENT DETAILS");
                System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));
                System.out.printf("│ %-50.50s │%n", "  Store: " + vendor.getStore().getStoreName());
                System.out.printf("│ %-50.50s │%n", "  Handled By: " + vendor.getFirstName() + " " + vendor.getLastName());

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50.50s │%n", "  TRANSACTION AMOUNT: " + String.format("PHP %,.2f", payment.getAmount()));
                System.out.printf("│ %-50s │%n", "");
                System.out.println("└────────────────────────────────────────────────────┘");

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

                System.out.println();
                System.out.println("┌────────────────────────────────────────────────────┐");
                System.out.printf("│ %-50s │%n", "                  CASH OUT HISTORY");
                System.out.println("├────────────────────────────────────────────────────┤");

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50.50s │%n", "  Cash Out ID: " + cashOut.getCashOutId());
                System.out.printf("│ %-50.50s │%n", "  Date: " + ConsoleUi.formatDateTime(cashOut.getDateTime()));
                System.out.printf("│ %-50.50s │%n", "  Status: " + cashOut.getStatus());

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50s │%n", "  TRANSACTION DETAILS");
                System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

                System.out.printf("│ %-50.50s │%n", "  Vendor: " + vendor.getFirstName() + " " + vendor.getLastName());
                System.out.printf("│ %-50.50s │%n", "  Store: " + vendor.getStore().getStoreName());
                System.out.printf("│ %-50.50s │%n", "  Processed By: " + cashOut.getCashier().getFirstName() + " " + cashOut.getCashier().getLastName());

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));
                System.out.printf("│ %-50.50s │%n", "  CASH OUT AMOUNT: " + String.format("PHP %,.2f", cashOut.getAmount()));
                System.out.printf("│ %-50s │%n", "");
                System.out.println("└────────────────────────────────────────────────────┘");

                found = true;
            }
        }

        if (!found) {
            ConsoleUi.showInfo("No cash out records found.");
        }
    }
}
