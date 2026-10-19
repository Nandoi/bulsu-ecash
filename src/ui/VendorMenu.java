package ui;

import java.util.Scanner;
import entities.user.Vendor;

public class VendorMenu {

    public static void show(Vendor vendor, Scanner sc) {
        boolean running = true;

        while (running) {
            ConsoleUi.showSection("VENDOR MENU");

            ConsoleUi.showInfo("Vendor: " + vendor.getFirstName());
            ConsoleUi.showInfo("Store: " + vendor.getStore().getStoreName());
            ConsoleUi.showBalance("Store Wallet Balance", vendor.getStore().getStoreWallet().getBalance());

            System.out.println();
            ConsoleUi.showOption(1, "Process Payment");
            ConsoleUi.showExitOption("Logout");

            System.out.print("\nEnter Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    ConsoleUi.showInfo("Payment processing will be connected next.");
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

    private static void pause(Scanner sc) {
        System.out.println();
        System.out.print("Press ENTER to continue...");
        sc.nextLine();
    }
}