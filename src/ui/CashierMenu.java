package ui;

import java.time.LocalDateTime;
import java.util.Scanner;

import data.DataStore;
import entities.transaction.TopUp;
import entities.user.Cashier;
import enums.TransactionStatus;
import services.TopUpService;

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
                    processTopUp(cashier, sc);
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

    private static void processTopUp(Cashier cashier, Scanner sc) {
        ConsoleUi.showSection("PROCESS STUDENT TOP UP");

        System.out.print("Student Number: ");
        String studentNumber = sc.nextLine();

        System.out.print("Amount: PHP ");
        double amount = sc.nextDouble();
        sc.nextLine();

        int topUpId = DataStore.generateNextTopUpId();
        String dateTime = LocalDateTime.now().toString();

        TopUp topUp = TopUpService.processTopUp(topUpId, amount, dateTime, studentNumber, cashier);

        if (topUp == null) {
            ConsoleUi.showError("Top Up could not be processed.");
            ConsoleUi.showInfo("Check the Student Number and amount.");
            pause(sc);
            return;
        }

        if (topUp.getStatus() == TransactionStatus.FAILED) {
            ConsoleUi.showError("Top Up failed.");
            ConsoleUi.showInfo("Check the student's wallet status.");
            pause(sc);
            return;
        }

        ConsoleUi.showSuccess("Top Up successful.");
        ConsoleUi.showInfo("Top Up ID: " + topUp.getTopUpId());
        ConsoleUi.showBalance("Amount", topUp.getAmount());
        ConsoleUi.showBalance("Student Wallet Balance", topUp.getStudentWallet().getBalance());

        pause(sc);
    }

    private static void pause(Scanner sc) {
        System.out.println();
        System.out.print("Press ENTER to continue...");
        sc.nextLine();
    }
}