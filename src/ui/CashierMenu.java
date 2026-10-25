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
            ConsoleUi.showOption(5, "Search Student Top Up Records");
            ConsoleUi.showOption(6, "View Transaction Summary");
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

                case 5:
                    searchStudentTopUpRecords(sc);
                    ConsoleUi.pause(sc);
                    break;

                case 6:
                    showTransactionSummary(cashier);
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

        //Ask for student number and validation
        System.out.print("Student Number: ");
        String studentNumber = sc.nextLine();

        Student student = DataStore.findStudentByStudentNumber(studentNumber);

        if (student == null) {
            ConsoleUi.showError("Student not found.");
            ConsoleUi.pause(sc);
            return;
        }

        //Ask  for amount and validation
        System.out.print("Amount: PHP ");
        double amount = sc.nextDouble();
        sc.nextLine();

        if (amount <= 0) {
            ConsoleUi.showError("Amount must be greater than 0.");
            ConsoleUi.pause(sc);
            return;
        }

        //Ask for confirmation and display transaction details
        ConsoleUi.showSection("CONFIRM TOP UP");
        System.out.println("Student: " + student.getFirstName() + " " + student.getLastName());
        System.out.println("Student Number: " + student.getStudentNumber());
        System.out.printf("Current Balance: PHP %.2f%n", student.getStudentWallet().getBalance());
        System.out.printf("Top Up Amount: PHP %.2f%n", amount);
        System.out.printf("Balance After Top Up: PHP %.2f%n", student.getStudentWallet().getBalance() + amount);
        System.out.println("Handled By: " + cashier.getFirstName() + " " + cashier.getLastName());

        //A boolean method to confirm the transaction, if not confirmed, return to the menu
        if (!ConsoleUi.confirmTransaction(sc)) {
            ConsoleUi.showInfo("Top Up cancelled.");
            ConsoleUi.pause(sc);
            return;
        }

        //If boolean method returns true, process the top up
        TopUp topUp = TopUpService.processTopUp(amount, studentNumber, cashier);

        //If topupservice returns null, display error message and return to the menu
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
        ConsoleUi.showBalance("Student Wallet Balance", topUp.getStudentWallet().getBalance());

        ConsoleUi.pause(sc);
    }

    private static void processCashOut(Cashier cashier, Scanner sc) {
        ConsoleUi.showSection("PROCESS VENDOR CASH OUT");

        //Ask for vendor ID and validation
        System.out.print("Vendor ID: ");
        int vendorId = sc.nextInt();
        sc.nextLine();

        Vendor vendor = DataStore.findVendorByUserId(vendorId);

        if (vendor == null) {
            ConsoleUi.showError("Vendor not found.");
            ConsoleUi.pause(sc);
            return;
        }

        //Ask for amount and validation
        System.out.print("Amount: PHP ");
        double amount = sc.nextDouble();
        sc.nextLine();

        if (amount <= 0) {
            ConsoleUi.showError("Amount must be greater than 0.");
            ConsoleUi.pause(sc);
            return;
        }

        if (amount > vendor.getStore().getStoreWallet().getBalance()) {
            ConsoleUi.showError("Insufficient store wallet balance.");
            ConsoleUi.pause(sc);
            return;
        }

        //Ask for confirmation and display transaction details
        ConsoleUi.showSection("CONFIRM CASH OUT");
        System.out.println("Vendor: " + vendor.getFirstName() + " " + vendor.getLastName());
        System.out.println("Vendor ID: " + vendor.getUserId());
        System.out.println("Store: " + vendor.getStore().getStoreName());
        System.out.printf("Cash Out Amount: PHP %.2f%n", amount);
        System.out.println("Handled By: " + cashier.getFirstName() + " " + cashier.getLastName());

        //A boolean method to confirm the transaction, if not confirmed, return to the menu
        if (!ConsoleUi.confirmTransaction(sc)) {
            ConsoleUi.showInfo("Cash Out cancelled.");
            ConsoleUi.pause(sc);
            return;
        }

        //If boolean method returns true, process the cash out
        CashOut cashOut = CashOutService.processCashOut(amount, vendor, cashier);

        //If cashoutservice returns null, display error message and return to the menu
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
        ConsoleUi.showBalance("Store Wallet Balance", cashOut.getStoreWallet().getBalance());

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

    private static void searchStudentTopUpRecords(Scanner sc){
        ConsoleUi.showSection("SEARCH STUDENT TOP UP RECORDS");

        //Ask for student number and validation
        System.out.print("Student Number: ");
        String studentNumber = sc.nextLine();

        Student student = DataStore.findStudentByStudentNumber(studentNumber);

        if (student == null) {
            ConsoleUi.showError("Student not found.");
            return;
        }

        boolean found = false;

        //Filter top up records belonging to the student
        for (TopUp topUp : DataStore.getTopUps()) {
            if (topUp.getStudent() == student) {
                System.out.println();
                System.out.println("Top Up ID: " + topUp.getTopUpId());
                System.out.println("Type: " + topUp.getTransactionType());
                System.out.println("Date: " + topUp.getDateTime());
                System.out.println("Status: " + topUp.getStatus());
                System.out.printf("Amount: PHP %.2f%n", topUp.getAmount());
                System.out.println("Handled By: " + topUp.getCashier().getFirstName() + " " + topUp.getCashier().getLastName());
                System.out.println("----------------------------------------");

                found = true;
            }
        }

        if (!found){
            ConsoleUi.showInfo("No top up records found for this student.");
        }
    }

    private static void showTransactionSummary(Cashier cashier) {
        ConsoleUi.showSection("TRANSACTION SUMMARY");
        int successfulTopUps = 0;
        int failedTopUps = 0;
        double totalTopUpAmount = 0;
        
        int successfulCashOuts = 0;
        int failedCashOuts = 0;
        double totalCashOutAmount = 0;

        //Count top up transactions handled by the cashier
        for (TopUp topUp : DataStore.getTopUps()) {
            if (topUp.getCashier() == cashier) {
                if (topUp.getStatus() == TransactionStatus.SUCCESSFUL) {
                    successfulTopUps++;
                    totalTopUpAmount += topUp.getAmount();
                } else if (topUp.getStatus() == TransactionStatus.FAILED) {
                    failedTopUps++;
                }
            }
        }

        //Count cash out transactions handled by the cashier
        for (CashOut cashOut : DataStore.getCashOuts()) {
            if (cashOut.getCashier() == cashier) {
                if (cashOut.getStatus() == TransactionStatus.SUCCESSFUL) {
                    successfulCashOuts++;
                    totalCashOutAmount += cashOut.getAmount();
                } else if (cashOut.getStatus() == TransactionStatus.FAILED) {
                    failedCashOuts++;
                }
            }
        }

        System.out.println("Cashier: " + cashier.getFirstName() + " " + cashier.getLastName());

        System.out.println();
        System.out.println("TOP UPS");
        System.out.println("Successful Transactions: " + successfulTopUps);
        System.out.println("Failed Transactions: " + failedTopUps);
        System.out.printf("Total Top Up Amount: PHP %.2f%n", totalTopUpAmount);

        System.out.println();
        System.out.println("CASH OUTS");
        System.out.println("Successful Transactions: " + successfulCashOuts);
        System.out.println("Failed Transactions: " + failedCashOuts);
        System.out.printf("Total Cash Out Amount: PHP %.2f%n", totalCashOutAmount);
    }
}
