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
            ConsoleUi.showSection("VENDOR MENU");

            ConsoleUi.showInfo("Vendor: " + vendor.getFirstName());
            ConsoleUi.showInfo("Store: " + vendor.getStore().getStoreName());
            ConsoleUi.showBalance("Store Wallet Balance", vendor.getStore().getStoreWallet().getBalance());

            System.out.println();
            ConsoleUi.showOption(1, "Process Payment");
            ConsoleUi.showOption(2, "View Payment History");
            ConsoleUi.showOption(3, "View Cash Out History");
            ConsoleUi.showExitOption("Logout");

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
        ConsoleUi.showSection("CONFIRM PAYMENT");
        System.out.println("Student: " + student.getFirstName() + " " + student.getLastName());
        System.out.println("Student Number: " + student.getStudentNumber());
        System.out.println("Card UID: " + card.getCardUid());
        System.out.printf("Payment Amount: PHP %.2f%n", amount);
        System.out.println("Store: " + vendor.getStore().getStoreName());
        System.out.println("Handled By: " + vendor.getFirstName() + " " + vendor.getLastName());

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
        ConsoleUi.showSuccess("Payment successful.");
        ConsoleUi.showInfo("Payment ID: " + payment.getPaymentId());
        ConsoleUi.showBalance("Store Wallet Balance", vendor.getStore().getStoreWallet().getBalance());

        ConsoleUi.pause(sc);
    }

    private static void showPaymentHistory(Vendor vendor) {
        ConsoleUi.showSection("PAYMENT HISTORY");

        boolean found = false;

        for (Payment payment : DataStore.getPayments()) {
            if (payment.getVendor() == vendor) {
                Student student = payment.getCard().getStudent();

                System.out.println("Payment ID: " + payment.getPaymentId());
                System.out.println("Type: " + payment.getTransactionType());
                System.out.println("Date: " + payment.getDateTime());
                System.out.println("Status: " + payment.getStatus());
                System.out.println("Amount: PHP %.2f%n" + payment.getAmount());
                System.out.println();
                System.out.println("Paid By: " + student.getFirstName() + " " + student.getLastName());
                System.out.println("Student Number: " + student.getStudentNumber());
                System.out.println("Card UID: " + payment.getCard().getCardUid());
                System.out.println("Received By: " + payment.getVendor().getStore().getStoreName());
                System.out.println("Handled By: " + payment.getVendor().getFirstName() + " " + payment.getVendor().getLastName());
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
                System.out.println("Date: " + cashOut.getDateTime());
                System.out.println("Status: " + cashOut.getStatus());
                System.out.printf("Amount: PHP %.2f%n", cashOut.getAmount());
                System.out.println();
                System.out.println("Requested By: " + cashOut.getVendor().getFirstName() + " " + cashOut.getVendor().getLastName());
                System.out.println("Store: " + cashOut.getVendor().getStore().getStoreName());
                System.out.println("Processed By: " + cashOut.getCashier().getFirstName() + " " + cashOut.getCashier().getLastName());
                System.out.println("----------------------------------------");

                found = true;
            }
        }

        if (!found) {
            ConsoleUi.showInfo("No cash out records found.");
        }
    }
}
