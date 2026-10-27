package ui;

import java.util.Scanner;

import data.DataStore;
import entities.Account;
import entities.user.Student;
import entities.Card;
import services.RegistrationService;

public class RegistrationUi {

    public static void show(Scanner sc) {
        showRegistrationMenu();
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
        ConsoleUi.boxHeader("REGISTRATION RESULT");

        if (account == null) {
            // Failed registration
            ConsoleUi.boxLine("  Status: FAILED");

            ConsoleUi.boxDivider();

            ConsoleUi.boxLine("  Registration failed.");
            ConsoleUi.boxLine("  Check your authorized information.");

        } else {
            // Successful registration
            ConsoleUi.boxLine("  Status: SUCCESSFUL");

            ConsoleUi.boxDivider();

            ConsoleUi.boxLine("  Account ID: " + account.getAccountId());

            ConsoleUi.boxLine("  Role: "
                    + account.getUser().getUserType());

            // Display active card for Student accounts
            if (account.getUser() instanceof Student) {
                Student student = (Student) account.getUser();

                for (Card card : DataStore.getCards()) {
                    if (card.getStudent() == student && card.isActive()) {
                        ConsoleUi.boxLine("  Card UID: " + card.getCardUid());
                        break;
                    }
                }
            }

            ConsoleUi.boxDivider();

            ConsoleUi.boxLine( "  Use your Account ID and PIN to log in.");
        }

        ConsoleUi.boxFooter();

        ConsoleUi.pause(sc);
    }


    private static void showRegistrationMenu() {
        ConsoleUi.boxHeader("REGISTER ACCOUNT");

        ConsoleUi.boxLine("  [1] Student");
        ConsoleUi.boxLine("  [2] Vendor");
        ConsoleUi.boxLine("  [3] Cashier");
        ConsoleUi.boxLine("  [0] Back");

        ConsoleUi.boxFooter();
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