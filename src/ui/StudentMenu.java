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
                    reportLostCard(student);
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
    }

    private static void showTransactionHistory(Student student) {
        ConsoleUi.showSection("TRANSACTION HISTORY");

        boolean found = false;

        //Display payment transactions of the student
        for (Payment payment : DataStore.getPayments()) {
            if (payment.getStudentWallet() == student.getStudentWallet()) {
                System.out.println("Payment ID: " + payment.getPaymentId());
                System.out.println("Type: " + payment.getTransactionType());
                System.out.println("Date: " + payment.getDateTime());
                System.out.println("Status: " + payment.getStatus());
                System.out.printf("Amount: PHP %.2f%n", payment.getAmount());
                System.out.println();
                System.out.println("Paid To: " + payment.getVendor().getStore().getStoreName());
                System.out.println("Handled By: " + payment.getVendor().getFirstName() + " " + payment.getVendor().getLastName());
                System.out.println("Card UID: " + payment.getCard().getCardUid());
                System.out.println("----------------------------------------");

                found = true;
            }
        }

        //Display top up transactions of the student
        for (TopUp topUp : DataStore.getTopUps()) {
            if (topUp.getStudentWallet() == student.getStudentWallet()) {
                System.out.println("Top Up ID: " + topUp.getTopUpId());
                System.out.println("Type: " + topUp.getTransactionType());
                System.out.println("Date: " + topUp.getDateTime());
                System.out.println("Status: " + topUp.getStatus());
                System.out.printf("Amount: PHP %.2f%n", topUp.getAmount());
                System.out.println();
                System.out.println("Received From: Cashier " + topUp.getCashier().getFirstName() + " " + topUp.getCashier().getLastName());
                System.out.println("----------------------------------------");

                found = true;
            }
        }
        

        if (!found) {
            ConsoleUi.showInfo("No transaction records found.");
        }
    }

    private static void reportLostCard(Student student){
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

        activeCard.markLost();

        ConsoleUi.showSuccess("Card reported as lost.");
        System.out.println("New Status: " + activeCard.getStatus());
    }
}
