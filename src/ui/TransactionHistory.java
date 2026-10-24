package ui;

import data.DataStore;
import entities.transaction.CashOut;
import entities.transaction.Payment;
import entities.transaction.TopUp;
import entities.user.Cashier;
import entities.user.Student;
import entities.user.Vendor;

public class TransactionHistory {
    public static void showTransactionHistory(Student student) {
        ConsoleUi.showSection("TRANSACTION HISTORY");

        boolean found = false;

        for (Payment payment : DataStore.getPayments()) {
            if (payment.getStudentWallet() == student.getStudentWallet()) {
                System.out.println("Payment ID: " + payment.getPaymentId());
                System.out.println("Type: " + payment.getTransactionType());
                System.out.println("Amount: PHP " + payment.getAmount());
                System.out.println("Date: " + payment.getDateTime());
                System.out.println("Status: " + payment.getStatus());
                System.out.println("Store: " + payment.getVendor().getStore().getStoreName());
                System.out.println("----------------------------------------");

                found = true;
            }
        }

        for (TopUp topUp : DataStore.getTopUps()) {
            if (topUp.getStudentWallet() == student.getStudentWallet()) {
                System.out.println("Top Up ID: " + topUp.getTopUpId());
                System.out.println("Type: " + topUp.getTransactionType());
                System.out.println("Amount: PHP " + topUp.getAmount());
                System.out.println("Date: " + topUp.getDateTime());
                System.out.println("Status: " + topUp.getStatus());
                System.out.println("----------------------------------------");
                found = true;
            }
        }

        if (!found) {
            ConsoleUi.showInfo("No transaction records found.");
        }
    }
    
    public static void showPaymentHistory(Vendor vendor) {
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

    public static void showCashOutHistory(Vendor vendor) {
        ConsoleUi.showSection("CASH OUT HISTORY");

        boolean found = false;

        for (CashOut cashOut : DataStore.getCashOuts()) {
            if (cashOut.getVendor() == vendor) {
                System.out.println("Cash Out ID: " + cashOut.getCashOutId());
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

    public static void showTopUpHistory(Cashier cashier) {
        ConsoleUi.showSection("TOP UP RECORDS");

        boolean found = false;

        for (TopUp topUp : DataStore.getTopUps()) {
            if (topUp.getCashier() == cashier) {
                System.out.println("Top Up ID: " + topUp.getTopUpId());
                System.out.println("Amount: PHP " + topUp.getAmount());
                System.out.println("Date: " + topUp.getDateTime());
                System.out.println("Status: " + topUp.getStatus());
                System.out.println("----------------------------------------");

                found = true;
            }
        }

        if (!found) {
            ConsoleUi.showInfo("No top up records found.");
        }
    }

    public static void showCashOutHistory(Cashier cashier) {
        ConsoleUi.showSection("CASH OUT RECORDS");

        boolean found = false;

        for (CashOut cashOut : DataStore.getCashOuts()) {
            if (cashOut.getCashier() == cashier) {
                System.out.println("Cash Out ID: " + cashOut.getCashOutId());
                System.out.println("Vendor: " + cashOut.getVendor().getFirstName());
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
