package ui;

import java.util.Scanner;

import data.DataStore;
import entities.Account;
import entities.user.Student;
import entities.Card;
import services.RegistrationService;

public class RegistrationUi {

    public static void show(Scanner sc) {
        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "                 REGISTER ACCOUNT");
        System.out.println("├────────────────────────────────────────────────────┤");
        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  SELECT ACCOUNT TYPE");
        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  [1] Student");
        System.out.printf("│ %-50s │%n", "  [2] Vendor");
        System.out.printf("│ %-50s │%n", "  [3] Cashier");
        System.out.printf("│ %-50s │%n", "  [0] Back");
        System.out.printf("│ %-50s │%n", "");
        System.out.println("└────────────────────────────────────────────────────┘");
        int choice = ConsoleUi.readInt(sc, "\nEnter Role: ");

        switch (choice) {
            case 1:
                registerStudent(sc);
                break;

            case 2:
                registerVendor(sc);
                break;

            case 3:
                registerCashier(sc);
                break;

            case 0:
                return;

            default:
                ConsoleUi.showError("Invalid option.");
                ConsoleUi.pause(sc);
                break;
        }
    }

    private static void registerStudent(Scanner sc) {
        ConsoleUi.showSection("STUDENT REGISTRATION");
        System.out.print("Student Number: ");
        String studentNumber = sc.nextLine();
        String pin = getConfirmedPin(sc);
        if (pin == null) {
            return;
        }
        Account account = RegistrationService.registerStudent(pin, studentNumber);
        showRegistrationResult(account, sc);
    }

    private static void registerVendor(Scanner sc) {
        ConsoleUi.showSection("VENDOR REGISTRATION");
        int vendorId = ConsoleUi.readInt(sc, "Vendor ID: ");
        String pin = getConfirmedPin(sc);
        if (pin == null) {
            return;
        }
        Account account = RegistrationService.registerVendor(pin, vendorId);
        showRegistrationResult(account, sc);
    }

    private static void registerCashier(Scanner sc) {
        ConsoleUi.showSection("CASHIER REGISTRATION");
        int cashierId = ConsoleUi.readInt(sc, "Cashier ID: ");
        String pin = getConfirmedPin(sc);
        if (pin == null) {
            return;
        }
        Account account = RegistrationService.registerCashier(pin, cashierId);
        showRegistrationResult(account, sc);
    }

    private static void showRegistrationResult(Account account, Scanner sc) {
        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "               REGISTRATION RESULT");
        System.out.println("├────────────────────────────────────────────────────┤");
        System.out.printf("│ %-50s │%n", "");

        if (account == null) {
            System.out.printf("│ %-50s │%n", "  STATUS: FAILED");
            System.out.printf("│ %-50s │%n", "");
            System.out.printf("│ %-50s │%n", "  Registration failed.");
            System.out.printf("│ %-50s │%n", "  Check your authorized information.");

        } else {
            System.out.printf("│ %-50s │%n", "  STATUS: SUCCESSFUL");
            System.out.printf("│ %-50s │%n", "");
            System.out.printf("│ %-50.50s │%n", "  Account ID: " + account.getAccountId());
            System.out.printf("│ %-50.50s │%n", "  Role: " + account.getUser().getUserType());

            if (account.getUser() instanceof Student) {
                Student student = (Student) account.getUser();

                for (Card card : DataStore.getCards()) {
                    if (card.getStudent() == student && card.isActive()) {
                        System.out.printf("│ %-50.50s │%n", "  Card UID: " + card.getCardUid());
                        break;
                    }
                }
            }

            System.out.printf("│ %-50s │%n", "");
            System.out.printf("│ %-50s │%n", "  Use your Account ID and PIN to log in.");
        }

        System.out.printf("│ %-50s │%n", "");
        System.out.println("└────────────────────────────────────────────────────┘");

        ConsoleUi.pause(sc);
    }

    private static String getConfirmedPin(Scanner sc) {
        System.out.print("Create PIN: ");
        String pin = sc.nextLine().trim();
        if (!pin.matches("\\d{4}")) {
            ConsoleUi.showError("PIN must contain exactly 4 digits.");
            ConsoleUi.pause(sc);
            return null;
        }

        System.out.print("Confirm PIN: ");
        String confirmPin = sc.nextLine().trim();
        if (!pin.equals(confirmPin)) {
            ConsoleUi.showError("PINs do not match.");
            ConsoleUi.pause(sc);
            return null;
        }
        return pin;
    }
}