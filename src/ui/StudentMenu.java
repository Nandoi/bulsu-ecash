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
            ConsoleUi.showExitOption("Logout");

            System.out.print("\n  Enter Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    showProfile(student);
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

    private static void pause(Scanner sc) {
        System.out.println();
        System.out.print("Press ENTER to continue...");
        sc.nextLine();
    }
}