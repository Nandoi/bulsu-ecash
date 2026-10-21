package ui;

import java.util.Scanner;
import entities.user.Student;

import data.DataStore;
import entities.transaction.Payment;
import entities.transaction.TopUp;

public class StudentMenu {
    private static final double LOW_BALANCE_THRESHOLD = 100.00;

    public static void show(Student student, Scanner sc) {
        boolean running = true;

        while (running) {
            ConsoleUi.showSection("STUDENT MENU");

            ConsoleUi.showInfo("Welcome, " + student.getFirstName() + "!");
            ConsoleUi.showBalance("Wallet Balance", student.getStudentWallet().getBalance());
            if (student.getStudentWallet().getBalance() < LOW_BALANCE_THRESHOLD) {
                ConsoleUi.showInfo("Warning: Your wallet balance is below PHP "
                        + String.format("%.2f", LOW_BALANCE_THRESHOLD) + ".");
            }

            System.out.println();
            ConsoleUi.showOption(1, "View Profile");
            ConsoleUi.showOption(2, "View Transaction History");
            ConsoleUi.showExitOption("Logout");

            System.out.print("\n  Enter Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    showProfile(student);
                    pause(sc);
                    break;

                case 2:
                    showTransactionHistory(student);
                    pause(sc);
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

    private static void showProfile(Student student) {
        ConsoleUi.showSection("STUDENT PROFILE");

        System.out.println("Student Number: " + student.getStudentNumber());
        System.out.println("Name: " + student.getFirstName() + " " + student.getLastName());
        System.out.println("Program: " + student.getProgram());
        System.out.println("Year Level: " + student.getYearLevel());
        System.out.println("Email: " + student.getEmail());
    }

    private static void showTransactionHistory(Student student) {
        ConsoleUi.showSection("TRANSACTION HISTORY");

        boolean found = false;

        for (Payment payment : DataStore.getPayments()) {
            if (payment.getStudentWallet() == student.getStudentWallet()) {
                System.out.println("Payment ID: " + payment.getPaymentId());
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
                System.out.println("Type: TOP_UP");
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

    private static void pause(Scanner sc) {
        System.out.println();
        System.out.print("Press ENTER to continue...");
        sc.nextLine();
    }
}
