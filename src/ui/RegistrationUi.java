package ui;

import java.util.Scanner;

import data.DataStore;
import entities.Account;
import entities.user.Student;
import entities.Card;
import services.RegistrationService;

public class RegistrationUi {

    public static void show(Scanner sc) {
        ConsoleUi.showSection("REGISTER");
        ConsoleUi.showOption(1, "Student");
        ConsoleUi.showOption(2, "Vendor");
        ConsoleUi.showOption(3, "Cashier");
        ConsoleUi.showExitOption("Back");
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
        if (account == null) {
            ConsoleUi.showError("Registration failed.");
            ConsoleUi.showInfo("Check your authorized information.");
        } else {
            ConsoleUi.showSuccess("Registration successful.");
            ConsoleUi.showInfo("Account ID: " + account.getAccountId());
            ConsoleUi.showInfo("Role: " + account.getUser().getUserType());
            if (account.getUser() instanceof Student) {
                Student student = (Student) account.getUser();

                for (Card card : DataStore.getCards()) {
                    if (card.getStudent() == student && card.isActive()) {
                        ConsoleUi.showInfo("Card UID: " + card.getCardUid());
                        break;
                    }
                }
            }
            ConsoleUi.showInfo("Use your Account ID and PIN when logging in.");
        }
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