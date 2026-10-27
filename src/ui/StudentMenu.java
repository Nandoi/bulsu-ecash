package ui;

import java.util.Scanner;

import data.DataStore;
import enums.TransactionStatus;
import entities.transaction.Payment;
import entities.transaction.TopUp;
import entities.user.Student;
import entities.Card;

public class StudentMenu {
    public static void show(Student student, Scanner sc) {
        boolean running = true;

        while (running){
            showMenu(student);
            int choice = ConsoleUi.readInt(sc, "\nEnter Choice: ");

            switch (choice) {
                case 1:
                    showProfile(student);
                    ConsoleUi.pause(sc);
                    break;

                case 2:
                    showPaymentRecords(student);
                    ConsoleUi.pause(sc);
                    break;

                case 3:
                    showTopUpRecords(student);
                    ConsoleUi.pause(sc);
                    break;

                case 4:
                    showTransactionSummary(student);
                    ConsoleUi.pause(sc);
                    break;
                case 5:
                    reportLostCard(student, sc);
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

    private static void showProfile(Student student) {
        Card card = DataStore.findCardByStudent(student);

        String cardUid = "NO CARD";
        String cardStatus = "N/A";

        if (card != null) {
            cardUid = card.getCardUid();
            cardStatus = card.getStatus().toString();
        }

        ConsoleUi.boxHeader("STUDENT PROFILE");

        // Personal information
        ConsoleUi.boxLine("  PERSONAL INFORMATION");

        ConsoleUi.boxLine("  Student No.: "
                + student.getStudentNumber());

        ConsoleUi.boxLine("  Name: " + student.getFirstName()
                + " " + student.getLastName());

        ConsoleUi.boxLine("  Program: " + student.getProgram());
        ConsoleUi.boxLine("  Year Level: " + student.getYearLevel());

        ConsoleUi.boxLine("  Address: "
                + student.getFullAddress());

        ConsoleUi.boxLine("  Email: " + student.getEmail());

        ConsoleUi.boxDivider();

        // Card information
        ConsoleUi.boxLine("  CARD INFORMATION");
        ConsoleUi.boxLine("  Card UID: " + cardUid);
        ConsoleUi.boxLine("  Card Status: " + cardStatus);

        ConsoleUi.boxFooter();
    }

    private static void showPaymentRecords(Student student) {
        ConsoleUi.boxHeader("PAYMENT HISTORY");

        ConsoleUi.boxLine("  Student: " + student.getFirstName()
                + " " + student.getLastName());
        ConsoleUi.boxDivider();

        boolean found = false;

        for (Payment payment : DataStore.getPayments()) {
            if (payment.getStudentWallet() == student.getStudentWallet()) {
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
        ConsoleUi.boxLine("  #" + payment.getPaymentId()
                + " | " + payment.getStatus());

        ConsoleUi.boxLine("  "
                + ConsoleUi.formatDateTime(payment.getDateTime()));

        ConsoleUi.boxLine("  Store: "
                + payment.getVendor().getStore().getStoreName());

        ConsoleUi.boxLine("  Vendor: "
                + payment.getVendor().getFirstName()
                + " " + payment.getVendor().getLastName());

        ConsoleUi.boxLine("  Card: "
                + payment.getCard().getCardUid());

        if (payment.getStatus() == TransactionStatus.SUCCESSFUL) {
            ConsoleUi.boxLine("  PAID: "
                    + String.format("PHP %,.2f", payment.getAmount()));
        } else {
            ConsoleUi.boxLine("  ATTEMPTED: "
                    + String.format("PHP %,.2f", payment.getAmount()));
        }
    }


    private static void showTopUpRecords(Student student) {
        ConsoleUi.boxHeader("TOP-UP HISTORY");

        ConsoleUi.boxLine("  Student: " + student.getFirstName()
                + " " + student.getLastName());
        ConsoleUi.boxDivider();

        boolean found = false;

        for (TopUp topUp : DataStore.getTopUps()) {
            if (topUp.getStudentWallet() == student.getStudentWallet()) {
                if (found) {
                    ConsoleUi.boxRecordDivider();
                }

                showTopUpRecord(topUp);
                found = true;
            }
        }

        if (!found) {
            ConsoleUi.boxLine("  No top-up records found.");
        }

        ConsoleUi.boxFooter();
    }

    private static void showTopUpRecord(TopUp topUp) {
        ConsoleUi.boxLine("  #" + topUp.getTopUpId()
                + " | " + topUp.getStatus());

        ConsoleUi.boxLine("  "
                + ConsoleUi.formatDateTime(topUp.getDateTime()));

        ConsoleUi.boxLine("  Cashier: "
                + topUp.getCashier().getFirstName()
                + " " + topUp.getCashier().getLastName());

        if (topUp.getStatus() == TransactionStatus.SUCCESSFUL) {
            ConsoleUi.boxLine("  RECEIVED: "
                    + String.format("PHP %,.2f", topUp.getAmount()));
        } else {
            ConsoleUi.boxLine("  ATTEMPTED: "
                    + String.format("PHP %,.2f", topUp.getAmount()));
        }
    }

    private static void showTransactionSummary(Student student) {
        int paymentCount = 0;
        int topUpCount = 0;

        int successful = 0;
        int failed = 0;

        double totalPaid = 0;
        double totalReceived = 0;

        // Count payment transactions
        for (Payment payment : DataStore.getPayments()) {
            if (payment.getStudentWallet() == student.getStudentWallet()) {
                paymentCount++;

                if (payment.getStatus() == TransactionStatus.SUCCESSFUL) {
                    successful++;
                    totalPaid += payment.getAmount();

                } else if (payment.getStatus() == TransactionStatus.FAILED) {
                    failed++;
                }
            }
        }

        // Count top-up transactions
        for (TopUp topUp : DataStore.getTopUps()) {
            if (topUp.getStudentWallet() == student.getStudentWallet()) {
                topUpCount++;

                if (topUp.getStatus() == TransactionStatus.SUCCESSFUL) {
                    successful++;
                    totalReceived += topUp.getAmount();

                } else if (topUp.getStatus() == TransactionStatus.FAILED) {
                    failed++;
                }
            }
        }

        // Transaction summary UI
        ConsoleUi.boxHeader("TRANSACTION SUMMARY");
        ConsoleUi.boxLine("  Student: " + student.getFirstName() + " " + student.getLastName());
        ConsoleUi.boxLine("  Student No.: " + student.getStudentNumber());
        ConsoleUi.boxDivider();

        // Payment summary
        ConsoleUi.boxLine("  PAYMENT SUMMARY");
        ConsoleUi.boxLine("  Total Payments: " + paymentCount);
        ConsoleUi.boxLine("  Total Paid: " + String.format("PHP %,.2f", totalPaid));
        ConsoleUi.boxDivider();

        // Top-up summary
        ConsoleUi.boxLine("  TOP-UP SUMMARY");
        ConsoleUi.boxLine("  Total Top-Ups: " + topUpCount);
        ConsoleUi.boxLine("  Total Received: " + String.format("PHP %,.2f", totalReceived));
        ConsoleUi.boxDivider();

        // Overall summary
        ConsoleUi.boxLine("  OVERALL SUMMARY");
        ConsoleUi.boxLine("  Successful: " + successful + "  |  Failed: " + failed);
        ConsoleUi.boxLine("  Current Balance: " + String.format("PHP %,.2f", student.getStudentWallet().getBalance()));
        ConsoleUi.boxLine("  Current session records only");
        ConsoleUi.boxFooter();
    }

    private static void reportLostCard(Student student, Scanner sc) {
        Card activeCard = null;

        // Find the student's active card
        for (Card card : DataStore.getCards()) {
            if (card.getStudent() == student && card.isActive()) {
                activeCard = card;
                break;
            }
        }

        if (activeCard == null) {
            ConsoleUi.showSection("REPORT LOST CARD");
            ConsoleUi.showInfo("No active card found.");
            return;
        }

        // Show confirmation UI
        showLostCardConfirmation(activeCard);

        // Ask student to confirm
        if (!ConsoleUi.confirmTransaction(sc)) {
            ConsoleUi.showInfo("Report lost card cancelled.");
            return;
        }

        // Update card status
        activeCard.markLost();

        showLostCardResult(activeCard);
    }


    private static void showLostCardConfirmation(Card card) {
        ConsoleUi.boxHeader("REPORT LOST CARD");

        // Card information
        ConsoleUi.boxLine("  CARD INFORMATION");

        ConsoleUi.boxLine("  Card UID: " + card.getCardUid());
        ConsoleUi.boxLine("  Current Status: " + card.getStatus());

        ConsoleUi.boxDivider();

        // Warning
        ConsoleUi.boxLine("  WARNING");

        ConsoleUi.boxLine(
                "  Reporting this card as lost will deactivate it.");

        ConsoleUi.boxLine(
                "  This card can no longer be used for payments.");

        ConsoleUi.boxLine(
                "  Your wallet balance will remain unchanged.");

        ConsoleUi.boxFooter();
    }

    private static void showLostCardResult(Card card) {
        ConsoleUi.boxHeader("LOST CARD RESULT");

        ConsoleUi.boxLine("  Status: SUCCESSFUL");
        ConsoleUi.boxLine("  Card UID: " + card.getCardUid());
        ConsoleUi.boxLine("  New Card Status: " + card.getStatus());

        ConsoleUi.boxLine("");

        ConsoleUi.boxLine(
                "  Visit an authorized cashier for card replacement.");

        ConsoleUi.boxLine(
                "  Your wallet balance has been preserved.");

        ConsoleUi.boxFooter();
    }

    private static void showMenu(Student student) {
        Card card = DataStore.findCardByStudent(student);

        String cardStatus = "NO CARD";

        if (card != null){
            cardStatus = card.getStatus().toString();
        }

        String balance = String.format("PHP %,.2f", student.getStudentWallet().getBalance());

        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "                 STUDENT DASHBOARD");
        System.out.println("├────────────────────────────────────────────────────┤");

        // Student information
        System.out.printf("│ %-50.50s │%n", "  " + "Hello, " + student.getFirstName() + " " + student.getLastName());
        System.out.printf("│ %-50.50s │%n", "  Student No.: " + student.getStudentNumber());
        System.out.printf("│ %-50.50s │%n", "  Card Status: " + cardStatus);

        System.out.println("├────────────────────────────────────────────────────┤");

        // Wallet balance
        System.out.printf("│ %-50s │%n", "  AVAILABLE BALANCE");
        System.out.printf("│ %-50.50s │%n", "  " + balance);

        System.out.println("├────────────────────────────────────────────────────┤");

        // Menu options
        System.out.printf("│ %-50s │%n", "  [1] View Profile");
        System.out.printf("│ %-50s │%n", "  [2] Payment History");
        System.out.printf("│ %-50s │%n", "  [3] Top-Up History");
        System.out.printf("│ %-50s │%n", "  [4] Transaction History");
        System.out.printf("│ %-50s │%n", "  [5] Report Lost Card");
        System.out.printf("│ %-50s │%n", "  [0] Logout");

        System.out.println("└────────────────────────────────────────────────────┘");
    }
}
