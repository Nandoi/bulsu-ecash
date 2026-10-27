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
        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "                    BULSU ECASH");
        System.out.printf("│ %-50s │%n", "                   ACCOUNT LOGIN");
        System.out.println("├────────────────────────────────────────────────────┤");

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  Please enter your Account ID and PIN.");
        System.out.printf("│ %-50s │%n", "");

        System.out.println("└────────────────────────────────────────────────────┘");


        int accountId = ConsoleUi.readInt(sc, "Account ID: ");

        System.out.print("PIN: ");
        String pin = sc.nextLine();

        Account account = AuthenticationService.login(accountId, pin);

        if (account == null) {
            ConsoleUi.showError("Invalid Account ID or PIN.");
            ConsoleUi.pause(sc);
            return;
        }

        ConsoleUi.showSuccess("Login successful.");

        openUserMenu(account);
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