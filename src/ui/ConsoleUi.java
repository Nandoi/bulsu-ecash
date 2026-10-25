package ui;

import java.util.Scanner;

public class ConsoleUi {
    private static final String LINE = "============================================================";

    public static void showTitle() {
        System.out.println();
        System.out.println(LINE);
        System.out.println("                       BulSU eCash");
        System.out.println("              Campus Cashless Payment System");
        System.out.println(LINE);
    }

    public static void showSection(String title) {
        System.out.println();
        System.out.println(LINE);
        System.out.println("  " + title);
        System.out.println(LINE);
    }

    public static void showOption(int number, String text) {
        System.out.println("  [" + number + "] " + text);
    }

    public static void showExitOption(String text) {
        System.out.println("  [0] " + text);
    }

    public static void showSuccess(String message) {
        System.out.println();
        System.out.println("[SUCCESS] " + message);
    }

    public static void showError(String message) {
        System.out.println();
        System.out.println("[ERROR] " + message);
    }

    public static void showInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    public static void showBalance(String label, double amount) {
        System.out.printf("%s: PHP %.2f%n", label, amount);
    }

    public static boolean confirmTransaction(Scanner sc) {
        while (true) {
            System.out.println();
            showOption(1, "Confirm");
            showExitOption("Cancel");
            int choice = readInt(sc, "\nEnter Choice: ");

            if (choice == 1) {
                return true;
            }

            if (choice == 0) {
                return false;
            }

            showError("Invalid choice.");
        }
    }

    public static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();

            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                showError("Please enter a valid number.");
            }
        }
    }

    public static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();

            try {
                return Double.parseDouble(input.trim());
            } catch (NumberFormatException e) {
                showError("Please enter a valid amount.");
            }
        }
    }

    public static void pause(Scanner sc){
        System.out.println();
        System.out.print("Press ENTER to continue...");
        sc.nextLine();
    }
}
