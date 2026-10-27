package ui;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleUi {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");

    public static void showTitle() {
        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "                   BulSU eCash");
        System.out.printf("│ %-50s │%n", "          Campus Cashless Payment System");
        System.out.println("├────────────────────────────────────────────────────┤");
        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  MAIN MENU");
        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  [1] Login");
        System.out.printf("│ %-50s │%n", "  [2] Register");
        System.out.printf("│ %-50s │%n", "  [0] Exit");
        System.out.printf("│ %-50s │%n", "");
        System.out.println("└────────────────────────────────────────────────────┘");
    }

    public static void showSection(String title) {
        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50.50s │%n", "  " + title);
        System.out.println("└────────────────────────────────────────────────────┘");
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

    public static boolean confirmTransaction(Scanner sc) {
        while (true) {

            System.out.println();
            System.out.println("┌────────────────────────────────────────────────────┐");
            System.out.printf("│ %-50s │%n", "                   CONFIRM ACTION");
            System.out.println("├────────────────────────────────────────────────────┤");

            System.out.printf("│ %-50s │%n", "");
            System.out.printf("│ %-50s │%n", "  [1] Confirm");
            System.out.printf("│ %-50s │%n", "  [0] Cancel");
            System.out.printf("│ %-50s │%n", "");

            System.out.println("└────────────────────────────────────────────────────┘");

            int choice = readInt(sc, "Enter Choice: ");

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
                double amount = Double.parseDouble(input.trim());

                if (!Double.isFinite(amount)) {
                    showError("Please enter a valid amount.");
                    continue;
                }
                return amount;

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

    public static String formatDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime).format(DATE_TIME_FORMATTER);
    }
}
