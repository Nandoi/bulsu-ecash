package app;

import java.util.Scanner;

import data.TempData;

import entities.Account;
import entities.user.Cashier;
import entities.user.Student;
import entities.user.Vendor;
import ui.CashierMenu;
import ui.StudentMenu;
import ui.VendorMenu;
import ui.ConsoleUi;
import ui.RegistrationUi;
import services.AuthenticationService;

public class ConsoleApp {
    private static final Scanner sc = new Scanner(System.in);
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private static final long LOGIN_COOLDOWN_MS = 30_000L;

    private static int failedLoginAttempts = 0;
    private static long loginLockedUntil = 0;

    public static void start() {
        TempData.load();

        boolean running = true;

        while (running) {
            ConsoleUi.showTitle();
            int choice = ConsoleUi.readInt(sc, "\nEnter Choice: ");

            switch (choice) {
                case 1:
                    login();
                    break;

                case 2:
                    RegistrationUi.show(sc);
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
        ConsoleUi.showInfo("Thank you for using the app.");

        sc.close();
    }

    private static void login() {

        long currentTime = System.currentTimeMillis();

        // Check whether the cooldown is still active
        if (currentTime < loginLockedUntil) {

            long remainingSeconds =
                    (loginLockedUntil - currentTime + 999) / 1000;

            ConsoleUi.showError("Login temporarily locked.");
            ConsoleUi.showInfo("Try again in "
                    + remainingSeconds + " seconds.");

            ConsoleUi.pause(sc);
            return;
        }

        // Reset failed attempts after cooldown expires
        if (loginLockedUntil != 0) {
            failedLoginAttempts = 0;
            loginLockedUntil = 0;
        }

        ConsoleUi.boxHeader("ACCOUNT LOGIN");
        ConsoleUi.boxLine("  Enter your Account ID and PIN.");
        ConsoleUi.boxLine("  Maximum attempts: 3");
        ConsoleUi.boxFooter();

        while (failedLoginAttempts < MAX_LOGIN_ATTEMPTS) {

            int accountId = ConsoleUi.readInt(
                    sc, "\nAccount ID: ");

            System.out.print("PIN: ");
            String pin = sc.nextLine();

            // Authenticate user
            Account account = AuthenticationService.login(
                    accountId, pin);

            // Successful login
            if (account != null) {
                failedLoginAttempts = 0;
                loginLockedUntil = 0;

                ConsoleUi.showSuccess("Login successful.");

                openUserMenu(account);
                return;
            }

            // Failed login
            failedLoginAttempts++;

            int remainingAttempts =
                    MAX_LOGIN_ATTEMPTS - failedLoginAttempts;

            // Reached maximum failed attempts
            if (remainingAttempts == 0) {

                loginLockedUntil =
                        System.currentTimeMillis()
                        + LOGIN_COOLDOWN_MS;

                ConsoleUi.showError(
                        "Maximum login attempts reached.");

                ConsoleUi.showInfo(
                        "Login locked for 30 seconds.");

                ConsoleUi.pause(sc);
                return;
            }

            ConsoleUi.showError(
                    "Invalid Account ID or PIN.");

            ConsoleUi.showInfo(
                    "Remaining attempts: " + remainingAttempts);
        }
    }


    private static void openUserMenu(Account account){
        if (account.getUser() instanceof Student) {
            Student student = (Student) account.getUser();
            StudentMenu.show(student, sc);

        } else if (account.getUser() instanceof Vendor) {
            Vendor vendor = (Vendor) account.getUser();
            VendorMenu.show(vendor, sc);

        } else if (account.getUser() instanceof Cashier) {
            Cashier cashier = (Cashier) account.getUser();
            CashierMenu.show(cashier, sc);

        } else {
            ConsoleUi.showError("Unknown user type.");
            ConsoleUi.pause(sc);
        }
    }
}