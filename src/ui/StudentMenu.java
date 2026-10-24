package ui;

import java.util.Scanner;
import entities.user.Student;

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
            ConsoleUi.showExitOption("Logout");

            System.out.print("\n  Enter Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    showProfile(student);
                    ConsoleUi.pause(sc);
                    break;

                case 2:
                    TransactionHistory.showTransactionHistory(student);
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
        System.out.println("Email: " + student.getEmail());
    }
}
