package ui;

import java.util.Scanner;
import data.DataStore;
import entities.transaction.CashOut;
import entities.transaction.TopUp;
import entities.user.Cashier;
import entities.user.Student;
import entities.user.Vendor;
import enums.TransactionStatus;
import services.CashOutService;
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
            ConsoleUi.showOption(3, "View Top Up Records");
            ConsoleUi.showOption(4, "View Cash Out Records");   
            ConsoleUi.showExitOption("Logout");

            System.out.print("\nEnter Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    processTopUp(cashier, sc);
                    break;

                case 2:
                    processCashOut(cashier, sc);
                    break;

                case 3:
                    showTopUpHistory(cashier);
                    ConsoleUi.pause(sc);
                    break;

                case 4:
                    showCashOutHistory(cashier);
                    ConsoleUi.pause(sc);
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
    }

    private static void processTopUp(Cashier cashier, Scanner sc) {
        ConsoleUi.showSection("PROCESS STUDENT TOP UP");

        System.out.print("Student Number: ");
        String studentNumber = sc.nextLine();

        System.out.print("Amount: PHP ");
        double amount = sc.nextDouble();
        sc.nextLine();

        TopUp topUp = TopUpService.processTopUp(amount, studentNumber, cashier);

        if (topUp == null) {
            ConsoleUi.showError("Top Up could not be processed.");
            ConsoleUi.showInfo("Check the Student Number and amount.");
            ConsoleUi.pause(sc);
            return;
        }

        if (topUp.getStatus() == TransactionStatus.FAILED) {
            ConsoleUi.showError("Top Up failed.");
            ConsoleUi.showInfo("Check the student's wallet status.");
            ConsoleUi.pause(sc);
            return;
        }

        ConsoleUi.showSuccess("Top Up successful.");
        ConsoleUi.showInfo("Top Up ID: " + topUp.getTopUpId());
        ConsoleUi.showBalance("Amount", topUp.getAmount());
        ConsoleUi.showBalance("Student Wallet Balance", topUp.getStudentWallet().getBalance());

        ConsoleUi.showReceipt(
            topUp.getTransactionType(), 
            topUp.getTopUpId(), 
            topUp.getAmount(), 
            topUp.getDateTime(),
            "Cashier " + cashier.getFirstName(), 
            "Student " + studentNumber
        );

        ConsoleUi.pause(sc);
    }

    private static void processCashOut(Cashier cashier, Scanner sc) {
        ConsoleUi.showSection("PROCESS VENDOR CASH OUT");

        System.out.print("Vendor ID: ");
        int vendorId = sc.nextInt();
        sc.nextLine();

        System.out.print("Amount: PHP ");
        double amount = sc.nextDouble();
        sc.nextLine();

        Vendor vendor = DataStore.findVendorByUserId(vendorId);

        if (vendor == null) {
            ConsoleUi.showError("Vendor not found.");
            ConsoleUi.pause(sc);
            return;
        }

        CashOut cashOut = CashOutService.processCashOut(amount, vendor, cashier);

        if (cashOut == null) {
            ConsoleUi.showError("Cash Out could not be processed.");
            ConsoleUi.showInfo("Check the Vendor ID and amount.");
            ConsoleUi.pause(sc);
            return;
        }

        if (cashOut.getStatus() == TransactionStatus.FAILED) {
            ConsoleUi.showError("Cash Out failed.");
            ConsoleUi.showInfo("Check the store wallet balance or wallet status.");
            ConsoleUi.pause(sc);
            return;
        }

        ConsoleUi.showSuccess("Cash Out successful.");
        ConsoleUi.showInfo("Cash Out ID: " + cashOut.getCashOutId());
        ConsoleUi.showInfo("Vendor: " + vendor.getFirstName());
        ConsoleUi.showBalance("Amount", cashOut.getAmount());
        ConsoleUi.showBalance("Store Wallet Balance", cashOut.getStoreWallet().getBalance());

        ConsoleUi.showReceipt(
            cashOut.getTransactionType(), 
            cashOut.getCashOutId(), 
            cashOut.getAmount(), 
            cashOut.getDateTime(),
            vendor.getStore().getStoreName(), 
            "Cashier " + cashier.getFirstName()
        );

        ConsoleUi.pause(sc);
    }

    private static void showTopUpHistory(Cashier cashier) {
        ConsoleUi.showSection("TOP UP RECORDS");

        boolean found = false;

        for (TopUp topUp : DataStore.getTopUps()) {
            if (topUp.getCashier() == cashier) {
                Student student = topUp.getStudent();

                System.out.println("Top Up ID: " + topUp.getTopUpId());
                System.out.println("Type: " + topUp.getTransactionType());
                System.out.println("Date: " + topUp.getDateTime());
                System.out.println("Status: " + topUp.getStatus());
                System.out.printf("Amount: PHP %.2f%n", topUp.getAmount());
                System.out.println();
                System.out.println("Received By: " + student.getFirstName() + " " + student.getLastName());
                System.out.println("Student Number: " + student.getStudentNumber());
                System.out.println("Handled By: " + topUp.getCashier().getFirstName() + " " + topUp.getCashier().getLastName());
                System.out.println("----------------------------------------");

                found = true;
            }
        }

        if (!found) {
            ConsoleUi.showInfo("No top up records found.");
        }
    }

    private static void showCashOutHistory(Cashier cashier) {
        ConsoleUi.showSection("CASH OUT RECORDS");

        boolean found = false;

        for (CashOut cashOut : DataStore.getCashOuts()) {
            if (cashOut.getCashier() == cashier) {
                System.out.println("Cash Out ID: " + cashOut.getCashOutId());
                System.out.println("Type: " + cashOut.getTransactionType());
                System.out.println("Date: " + cashOut.getDateTime());
                System.out.println("Status: " + cashOut.getStatus());
                System.out.printf("Amount: PHP %.2f%n", cashOut.getAmount());
                System.out.println();
                System.out.println("Requested By: " + cashOut.getVendor().getFirstName() + " " + cashOut.getVendor().getLastName());
                System.out.println("Store: " + cashOut.getVendor().getStore().getStoreName());
                System.out.println("Handled By: " + cashOut.getCashier().getFirstName() + " " + cashOut.getCashier().getLastName());
                System.out.println("----------------------------------------");

                found = true;
            }
        }

        if (!found) {
            ConsoleUi.showInfo("No cash out records found.");
        }
    }
}
