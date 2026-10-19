package ui;

import java.util.Scanner;
import entities.user.Cashier;

public class CashierMenu {

    public static void show(Cashier cashier, Scanner sc) {
        boolean running = true;

        while (running) {
            ConsoleUi.showSection("CASHIER MENU");

            ConsoleUi.showInfo("Cashier: " + cashier.getFirstName());

            System.out.println();
            ConsoleUi.showOption(1, "Process Student Top Up");
            ConsoleUi.showOption(2, "Process Vendor Cash Out");
            ConsoleUi.showExitOption("Logout");

            System.out.print("\nEnter Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    ConsoleUi.showInfo("Top Up processing will be connected next.");
                    pause(sc);
                    break;

                case 2:
                    ConsoleUi.showInfo("Cash Out processing will be connected next.");
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