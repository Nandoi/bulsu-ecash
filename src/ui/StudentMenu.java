package ui;

import java.util.Scanner;

import data.DataStore;
import entities.transaction.Payment;
import entities.transaction.TopUp;
import entities.user.Student;
import entities.Card;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

import entities.transaction.TransactionRecord;

public class StudentMenu {
    public static void show(Student student, Scanner sc) {
        boolean running = true;

        while (running) {
            ConsoleUi.showSection("STUDENT MENU");
            ConsoleUi.showInfo("Welcome, " + student.getFirstName() + "!");
            ConsoleUi.showBalance("Wallet Balance", student.getStudentWallet().getBalance());
            System.out.println();
            ConsoleUi.showOption(1, "View Profile");
            ConsoleUi.showOption(2, "View Transaction History");
            ConsoleUi.showOption(3, "Report Card as Lost");
            ConsoleUi.showExitOption("Logout");

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
        ConsoleUi.showSection("STUDENT PROFILE");

        System.out.println("Student Number: " + student.getStudentNumber());
        System.out.println("Name: " + student.getFirstName() + " " + student.getLastName());
        System.out.println("Program: " + student.getProgram());
        System.out.println("Year Level: " + student.getYearLevel());
        System.out.println("Address: " + student.getFullAddress());
        System.out.println("Email: " + student.getEmail());

        Card card = DataStore.findCardByStudent(student);

        System.out.println("Card UID: " + card.getCardUid());
        System.out.println("Card Status: " + card.getStatus());
    }

    private static void showTransactionHistory(Student student) {
        ConsoleUi.showSection("TRANSACTION HISTORY");

        ArrayList<TransactionRecord> transactions = new ArrayList<>();

        for (Payment payment : DataStore.getPayments()) {
            if (payment.getStudentWallet() == student.getStudentWallet()) {
                transactions.add(payment);
            }
        }

        for (TopUp topUp : DataStore.getTopUps()) {
            if (topUp.getStudentWallet() == student.getStudentWallet()) {
                transactions.add(topUp);
            }
        }

        transactions.sort(Comparator.comparing(transaction -> LocalDateTime.parse(transaction.getDateTime())));

        if (transactions.isEmpty()) {
            ConsoleUi.showInfo("No transaction records found.");
            return;
        }

        for (TransactionRecord transaction : transactions) {
            if (transaction instanceof Payment){
                Payment payment = (Payment) transaction;

                System.out.println("Payment ID: " + payment.getPaymentId());
                System.out.println("Type: " + payment.getTransactionType());
                System.out.println("Date: " + ConsoleUi.formatDateTime(payment.getDateTime()));
                System.out.println("Status: " + payment.getStatus());
                System.out.printf("Amount: PHP %.2f%n", payment.getAmount());
                System.out.println();
                System.out.println("Paid To: " + payment.getVendor().getStore().getStoreName());
                System.out.println("Handled By: "
                        + payment.getVendor().getFirstName() + " "
                        + payment.getVendor().getLastName());
                System.out.println("Card UID: " + payment.getCard().getCardUid());

            }else if (transaction instanceof TopUp){
                TopUp topUp = (TopUp) transaction;

                System.out.println("Top Up ID: " + topUp.getTopUpId());
                System.out.println("Type: " + topUp.getTransactionType());
                System.out.println("Date: " + ConsoleUi.formatDateTime(topUp.getDateTime()));
                System.out.println("Status: " + topUp.getStatus());
                System.out.printf("Amount: PHP %.2f%n", topUp.getAmount());
                System.out.println();
                System.out.println("Received From: Cashier "
                        + topUp.getCashier().getFirstName() + " "
                        + topUp.getCashier().getLastName());
            }
            System.out.println("----------------------------------------");
        }
    }

    private static void reportLostCard(Student student, Scanner sc){
        ConsoleUi.showSection("REPORT LOST CARD");

        Card activeCard = null;

        for (Card card : DataStore.getCards()) {
            if (card.getStudent() == student && card.isActive()) {
                activeCard = card;
                break;
            }
        }

        if (activeCard == null) {
            ConsoleUi.showInfo("No active card found.");
            return;
        }

        System.out.println("Card UID: " + activeCard.getCardUid());
        System.out.println("Current Status: " + activeCard.getStatus());

        if (!ConsoleUi.confirmTransaction(sc)) {
            System.out.println();
            ConsoleUi.showInfo("Report lost card cancelled.");
            return;
        }

        activeCard.markLost();

        ConsoleUi.showSuccess("Card reported as lost.");
        System.out.println("New Status: " + activeCard.getStatus());
    }
}
