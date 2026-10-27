package ui;

import java.util.Scanner;

import data.DataStore;
import entities.transaction.Payment;
import entities.transaction.TopUp;
import entities.user.Student;
import entities.Card;

public class StudentMenu {
    public static void show(Student student, Scanner sc) {
        boolean running = true;

        while (running){
            Card card = DataStore.findCardByStudent(student);

            String cardStatus = "NO CARD";

            if (card != null){
                cardStatus = card.getStatus().toString();
            }

            String balance = String.format("PHP %,.2f", student.getStudentWallet().getBalance());

            showMenu(student, balance, cardStatus);
            int choice = ConsoleUi.readInt(sc, "\nEnter Choice: ");

            switch (choice) {
                case 1:
                    showProfile(student);
                    ConsoleUi.pause(sc);
                    break;

                case 2:
                    showTransactionHistory(student);
                    ConsoleUi.pause(sc);
                    break;

                case 3:
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

        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "                  STUDENT PROFILE");
        System.out.println("├────────────────────────────────────────────────────┤");

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  PERSONAL INFORMATION");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50.50s │%n", "  Student Number: " + student.getStudentNumber());
        System.out.printf("│ %-50.50s │%n", "  Name: " + student.getFirstName() + " " + student.getLastName());
        System.out.printf("│ %-50.50s │%n", "  Program: " + student.getProgram());
        System.out.printf("│ %-50.50s │%n", "  Year Level: " + student.getYearLevel());
        System.out.printf("│ %-50.50s │%n", "  Address: " + student.getFullAddress());
        System.out.printf("│ %-50.50s │%n", "  Email: " + student.getEmail());

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  CARD INFORMATION");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50.50s │%n", "  Card UID: " + cardUid);
        System.out.printf("│ %-50.50s │%n", "  Card Status: " + cardStatus);

        System.out.printf("│ %-50s │%n", "");
        System.out.println("└────────────────────────────────────────────────────┘");
    }



    private static void showTransactionHistory(Student student) {
        ConsoleUi.showSection("TRANSACTION HISTORY");

        boolean found = false;

        // PAYMENT TRANSACTIONS
        for (Payment payment : DataStore.getPayments()) {
            if (payment.getStudentWallet() == student.getStudentWallet()) {

                System.out.println();
                System.out.println("┌────────────────────────────────────────────────────┐");
                System.out.printf("│ %-50s │%n", "                  PAYMENT RECEIPT");
                System.out.println("├────────────────────────────────────────────────────┤");

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50.50s │%n", "  Payment ID: " + payment.getPaymentId());
                System.out.printf("│ %-50.50s │%n", "  Date: " + ConsoleUi.formatDateTime(payment.getDateTime()));
                System.out.printf("│ %-50.50s │%n", "  Status: " + payment.getStatus());
                System.out.printf("│ %-50s │%n", "");

                System.out.printf("│ %-50s │%n", "  TRANSACTION DETAILS");
                System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

                System.out.printf("│ %-50.50s │%n", "  Store: " + payment.getVendor().getStore().getStoreName());
                System.out.printf("│ %-50.50s │%n", "  Handled By: " + payment.getVendor().getFirstName() + " " + payment.getVendor().getLastName());
                System.out.printf("│ %-50.50s │%n", "  Card UID: " + payment.getCard().getCardUid());

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));
                System.out.printf("│ %-50.50s │%n", "  TOTAL PAID: " + String.format("PHP %,.2f", payment.getAmount()));
                System.out.printf("│ %-50s │%n", "");

                System.out.println("└────────────────────────────────────────────────────┘");

                found = true;
            }
        }

        // TOP-UP TRANSACTIONS
        for (TopUp topUp : DataStore.getTopUps()) {
            if (topUp.getStudentWallet() == student.getStudentWallet()) {
                System.out.println();
                System.out.println("┌────────────────────────────────────────────────────┐");
                System.out.printf("│ %-50s │%n", "                   TOP-UP RECEIPT");
                System.out.println("├────────────────────────────────────────────────────┤");

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50.50s │%n", "  Top Up ID: " + topUp.getTopUpId());
                System.out.printf("│ %-50.50s │%n", "  Date: " + ConsoleUi.formatDateTime(topUp.getDateTime()));
                System.out.printf("│ %-50.50s │%n", "  Status: " + topUp.getStatus());
                System.out.printf("│ %-50s │%n", "");

                System.out.printf("│ %-50s │%n", "  TRANSACTION DETAILS");
                System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

                System.out.printf("│ %-50.50s │%n", "  Processed By: " + topUp.getCashier().getFirstName() + " " + topUp.getCashier().getLastName());

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));
                System.out.printf("│ %-50.50s │%n", "  TOTAL TOP-UP: " + String.format("PHP %,.2f", topUp.getAmount()));
                System.out.printf("│ %-50s │%n", "");

                System.out.println("└────────────────────────────────────────────────────┘");

                found = true;
            }
        }

        if (!found) {
            ConsoleUi.showInfo("No transaction records found.");
        }
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

        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "                  REPORT LOST CARD");
        System.out.println("├────────────────────────────────────────────────────┤");

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  CARD INFORMATION");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50s │%n", "  Card UID: " + activeCard.getCardUid());
        System.out.printf("│ %-50s │%n", "  Current Status: " + activeCard.getStatus());

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  WARNING");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50s │%n", "  Reporting this card as lost will deactivate it.");
        System.out.printf("│ %-50s │%n", "  The card can no longer be used for payments.");

        System.out.printf("│ %-50s │%n", "");
        System.out.println("└────────────────────────────────────────────────────┘");

        // Ask student to confirm
        if (!ConsoleUi.confirmTransaction(sc)) {
            ConsoleUi.showInfo("Report lost card cancelled.");
            return;
        }

        // Update card status
        activeCard.markLost();

        ConsoleUi.showSuccess("Card reported as lost.");
        ConsoleUi.showInfo("New Status: " + activeCard.getStatus());
    }

    private static void showMenu(Student student, String balance, String cardStatus) {
        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "                 STUDENT DASHBOARD");
        System.out.println("├────────────────────────────────────────────────────┤");

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  Welcome, " + student.getFirstName() + "!");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));
        System.out.printf("│ %-50s │%n", "  Student Number: " + student.getStudentNumber());
        System.out.printf("│ %-50s │%n", "  Wallet Balance: " + balance);
        System.out.printf("│ %-50s │%n", "  Card Status: " + cardStatus);
        System.out.printf("│ %-50s │%n", "");

        System.out.printf("│ %-50s │%n", "  ACCOUNT SERVICES");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));
        System.out.printf("│ %-50s │%n", "");

        System.out.printf("│ %-50s │%n", "  [1] View Profile");
        System.out.printf("│ %-50s │%n", "  [2] View Transaction History");
        System.out.printf("│ %-50s │%n", "  [3] Report Card as Lost");
        System.out.printf("│ %-50s │%n", "  [0] Logout");
        System.out.printf("│ %-50s │%n", "");

        System.out.println("└────────────────────────────────────────────────────┘");
    }
}
