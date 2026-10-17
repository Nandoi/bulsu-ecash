package app;

import java.util.Scanner;

import data.DataStore;
import data.TempData;

import entities.Account;

import services.AuthenticationService;
import services.RegistrationService;

import ui.ConsoleUi;

public class ConsoleApp {
    private static final Scanner sc = new Scanner(System.in);

    public static void start() {
        TempData.load();

        boolean running = true;

        while (running) {
            ConsoleUi.showTitle();

            ConsoleUi.showOption(1, "Login");
            ConsoleUi.showOption(2, "Register");
            ConsoleUi.showExitOption("Exit");

            System.out.print("\n  Enter Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    login();
                    break;

                case 2:
                    register();
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    ConsoleUi.showError("Invalid choice.");
                    pause();
                    break;
            }
        }

        ConsoleUi.showSection("BulSU eCash");
        ConsoleUi.showInfo("Thank you for using the app.");

        sc.close();
    }


    private static void login() {
        ConsoleUi.showSection("LOGIN");

        System.out.print("Account ID: ");
        int accountId = sc.nextInt();
        sc.nextLine();

        System.out.print("PIN: ");
        String pin = sc.nextLine();

        Account account = AuthenticationService.login(accountId, pin);

        if (account == null) {
            ConsoleUi.showError("Invalid Account ID or PIN.");
        } else {
            ConsoleUi.showSuccess("Login successful.");
            ConsoleUi.showInfo("Welcome, " + account.getUser().getFirstName() + "!");
            ConsoleUi.showInfo("Role: " + account.getUser().getUserType());
        }

        pause();
    }


    private static void register() {
        ConsoleUi.showSection("REGISTER");

        ConsoleUi.showOption(1, "Student");
        ConsoleUi.showOption(2, "Vendor");
        ConsoleUi.showOption(3, "Cashier");
        ConsoleUi.showExitOption("Back");

        System.out.print("\n  Select role: ");
        int role = sc.nextInt();
        sc.nextLine();

        switch (role) {
            case 1:
                registerStudent();
                break;

            case 2:
                registerVendor();
                break;

            case 3:
                registerCashier();
                break;

            case 0:
                return;

            default:
                ConsoleUi.showError("Invalid role.");
                pause();
                break;
        }
    }


    private static void registerStudent() {
        ConsoleUi.showSection("STUDENT REGISTRATION");

        System.out.println("Account ID: " + DataStore.getNextAccountId());

        System.out.print("Student Number: ");
        String studentNumber = sc.nextLine();

        System.out.print("Create PIN: ");
        String pin = sc.nextLine();

        System.out.print("Confirm PIN: ");
        String confirmPin = sc.nextLine();

        if (!pin.equals(confirmPin)) {
            ConsoleUi.showError("PINs do not match.");
            pause();
            return;
        }

        Account account = RegistrationService.registerStudent(pin, studentNumber);

        showRegistrationResult(account);
    }


    private static void registerVendor() {
        ConsoleUi.showSection("VENDOR REGISTRATION");

        System.out.println("Account ID: " + DataStore.getNextAccountId());

        System.out.print("Vendor ID: ");
        int vendorId = sc.nextInt();
        sc.nextLine();

        System.out.print("Create PIN: ");
        String pin = sc.nextLine();

        System.out.print("Confirm PIN: ");
        String confirmPin = sc.nextLine();

        if (!pin.equals(confirmPin)) {
            ConsoleUi.showError("PINs do not match.");
            pause();
            return;
        }

        Account account = RegistrationService.registerVendor(pin, vendorId);

        showRegistrationResult(account);
    }


    private static void registerCashier() {
        ConsoleUi.showSection("CASHIER REGISTRATION");

        System.out.println("Account ID: " + DataStore.getNextAccountId());

        System.out.print("Cashier ID: ");
        int cashierId = sc.nextInt();
        sc.nextLine();

        System.out.print("Create PIN: ");
        String pin = sc.nextLine();

        System.out.print("Confirm PIN: ");
        String confirmPin = sc.nextLine();

        if (!pin.equals(confirmPin)) {
            ConsoleUi.showError("PINs do not match.");
            pause();
            return;
        }

        Account account = RegistrationService.registerCashier(pin, cashierId);

        showRegistrationResult(account);
    }


    private static void showRegistrationResult(Account account) {
        if (account == null) {
            ConsoleUi.showError("Registration failed.");
            ConsoleUi.showInfo("Check your authorized information.");
        } else {
            ConsoleUi.showSuccess("Registration successful.");
            ConsoleUi.showInfo("Account ID: " + account.getAccountId());
            ConsoleUi.showInfo("Role: " + account.getUser().getUserType());
            ConsoleUi.showInfo("Use your Account ID and PIN when logging in.");
        }

        pause();
    }


    private static void pause() {
        System.out.println();
        System.out.print("Press ENTER to continue...");
        sc.nextLine();
    }
}