package ui;

import java.util.Scanner;
import data.DataStore;
import entities.transaction.CashOut;
import entities.transaction.TopUp;
import entities.user.Cashier;
import entities.user.Student;
import entities.user.Vendor;
import entities.Card;
import enums.CardStatus;
import enums.TransactionStatus;
import services.CashOutService;
import services.TopUpService;

public class CashierMenu {

    public static void show(Cashier cashier, Scanner sc) {
        boolean running = true;

        while (running) {
            String cashierName = cashier.getFirstName() + " " + cashier.getLastName();

            System.out.println();
            System.out.println("┌────────────────────────────────────────────────────┐");
            System.out.printf("│ %-50s │%n", "                  CASHIER DASHBOARD");
            System.out.println("├────────────────────────────────────────────────────┤");

            // Welcome
            System.out.printf("│ %-50s │%n", "");
            System.out.printf("│ %-50.50s │%n", "  Welcome, " + cashier.getFirstName() + "!");
            System.out.printf("│ %-50s │%n", "");

            // Cashier Information
            System.out.printf("│ %-50s │%n", "  CASHIER INFORMATION");
            System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));
            System.out.printf("│ %-50.50s │%n", "  Cashier ID: " + cashier.getUserId());
            System.out.printf("│ %-50.50s │%n", "  Cashier Name: " + cashierName);
            System.out.printf("│ %-50s │%n", "");

            // Transaction Services
            System.out.printf("│ %-50s │%n", "  TRANSACTION SERVICES");
            System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));
            System.out.printf("│ %-50s │%n", "  [1] Process Student Top Up");
            System.out.printf("│ %-50s │%n", "  [2] Process Vendor Cash Out");
            System.out.printf("│ %-50s │%n", "");

            // Transaction Records
            System.out.printf("│ %-50s │%n", "  TRANSACTION RECORDS");
            System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));
            System.out.printf("│ %-50s │%n", "  [3] View Top Up Records");
            System.out.printf("│ %-50s │%n", "  [4] View Cash Out Records");
            System.out.printf("│ %-50s │%n", "  [5] Search Student Top Up Records");
            System.out.printf("│ %-50s │%n", "  [6] View Transaction Summary");
            System.out.printf("│ %-50s │%n", "");

            // Card Management
            System.out.printf("│ %-50s │%n", "  CARD MANAGEMENT");
            System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));
            System.out.printf("│ %-50s │%n", "  [7] Replace Student Card");
            System.out.printf("│ %-50s │%n", "");

            // Logout
            System.out.printf("│ %-50s │%n", "  [0] Logout");
            System.out.printf("│ %-50s │%n", "");

            System.out.println("└────────────────────────────────────────────────────┘");


            int choice = ConsoleUi.readInt(sc, "\nEnter Choice: ");

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

                case 7:
                    replaceStudentCard(sc);
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
        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "                    BULSU ECASH");
        System.out.printf("│ %-50s │%n", "                  STUDENT TOP-UP");
        System.out.println("├────────────────────────────────────────────────────┤");

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  WALLET FUNDING");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));
        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  Enter the student's information");
        System.out.printf("│ %-50s │%n", "  to process a wallet top-up.");
        System.out.printf("│ %-50s │%n", "");

        System.out.println("└────────────────────────────────────────────────────┘");

        //Ask for student number and validation
        System.out.print("Student Number: ");
        String studentNumber = sc.nextLine();

        Student student = DataStore.findStudentByStudentNumber(studentNumber);

        if (student == null) {
            ConsoleUi.showError("Student not found.");
            ConsoleUi.pause(sc);
            return;
        }

        if (student.getStudentWallet() == null) {
            ConsoleUi.showError("Student does not have an eCash account.");
            ConsoleUi.pause(sc);
            return;
        }

        //Ask  for amount and validation
        double amount = ConsoleUi.readDouble(sc, "Amount: PHP ");

        if (amount <= 0) {
            ConsoleUi.showError("Amount must be greater than 0.");
            ConsoleUi.pause(sc);
            return;
        }

        //Ask for confirmation and display transaction details
        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "                 TOP-UP CONFIRMATION");
        System.out.println("├────────────────────────────────────────────────────┤");

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  STUDENT INFORMATION");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50.50s │%n", "  Student: " + student.getFirstName() + " " + student.getLastName());
        System.out.printf("│ %-50.50s │%n", "  Student Number: " + student.getStudentNumber());

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  TOP-UP DETAILS");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50.50s │%n", "  Current Balance: " + String.format("PHP %,.2f", student.getStudentWallet().getBalance()));
        System.out.printf("│ %-50.50s │%n", "  Top-Up Amount: " + String.format("PHP %,.2f", amount));
        System.out.printf("│ %-50.50s │%n", "  Balance After Top-Up: " + String.format("PHP %,.2f", student.getStudentWallet().getBalance() + amount));
        System.out.printf("│ %-50.50s │%n", "  Handled By: " + cashier.getFirstName() + " " + cashier.getLastName());

        System.out.printf("│ %-50s │%n", "");
        System.out.println("└────────────────────────────────────────────────────┘");

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


        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "                    BULSU ECASH");
        System.out.printf("│ %-50s │%n", "                    TOP-UP RECEIPT");
        System.out.println("├────────────────────────────────────────────────────┤");

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50.50s │%n", "  Transaction ID: " + topUp.getTopUpId());
        System.out.printf("│ %-50.50s │%n", "  Date: " + ConsoleUi.formatDateTime(topUp.getDateTime()));
        System.out.printf("│ %-50.50s │%n", "  Status: " + topUp.getStatus());

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  TRANSACTION DETAILS");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50.50s │%n", "  Student: " + student.getFirstName() + " " + student.getLastName());
        System.out.printf("│ %-50.50s │%n", "  Student Number: " + student.getStudentNumber());
        System.out.printf("│ %-50.50s │%n", "  Processed By: " + cashier.getFirstName() + " " + cashier.getLastName());

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50.50s │%n", "  TOTAL TOP-UP: " + String.format("PHP %,.2f", topUp.getAmount()));
        System.out.printf("│ %-50.50s │%n", "  New Balance: " + String.format("PHP %,.2f", topUp.getStudentWallet().getBalance()));
        System.out.printf("│ %-50s │%n", "");

        System.out.println("└────────────────────────────────────────────────────┘");


        ConsoleUi.pause(sc);
    }

    private static void processCashOut(Cashier cashier, Scanner sc) {
        ConsoleUi.showSection("PROCESS VENDOR CASH OUT");

        //Ask for vendor ID and validation
        int vendorId = ConsoleUi.readInt(sc, "Vendor ID: ");

        Vendor vendor = DataStore.findVendorByUserId(vendorId);

        if (vendor == null) {
            ConsoleUi.showError("Vendor not found.");
            ConsoleUi.pause(sc);
            return;
        }

        //Ask for amount and validation
        double amount = ConsoleUi.readDouble(sc, "Amount: PHP ");

        if (amount <= 0) {
            ConsoleUi.showError("Amount must be greater than 0.");
            ConsoleUi.pause(sc);
            return;
        }

        //Ask for confirmation and display transaction details
        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "                CASH-OUT CONFIRMATION");
        System.out.println("├────────────────────────────────────────────────────┤");

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  VENDOR INFORMATION");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50.50s │%n", "  Vendor: " + vendor.getFirstName() + " " + vendor.getLastName());
        System.out.printf("│ %-50.50s │%n", "  Vendor ID: " + vendor.getUserId());
        System.out.printf("│ %-50.50s │%n", "  Store: " + vendor.getStore().getStoreName());

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  CASH-OUT DETAILS");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50.50s │%n", "  Current Balance: " + String.format("PHP %,.2f", vendor.getStore().getStoreWallet().getBalance()));
        System.out.printf("│ %-50.50s │%n", "  Cash-Out Amount: " + String.format("PHP %,.2f", amount));
        System.out.printf("│ %-50.50s │%n", "  Balance After Cash-Out: " + String.format("PHP %,.2f", vendor.getStore().getStoreWallet().getBalance() - amount));
        System.out.printf("│ %-50.50s │%n", "  Handled By: " + cashier.getFirstName() + " " + cashier.getLastName());

        System.out.printf("│ %-50s │%n", "");
        System.out.println("└────────────────────────────────────────────────────┘");


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

        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "                    BULSU ECASH");
        System.out.printf("│ %-50s │%n", "                   CASH-OUT RECEIPT");
        System.out.println("├────────────────────────────────────────────────────┤");

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50.50s │%n", "  Transaction ID: " + cashOut.getCashOutId());
        System.out.printf("│ %-50.50s │%n", "  Date: " + ConsoleUi.formatDateTime(cashOut.getDateTime()));
        System.out.printf("│ %-50.50s │%n", "  Status: " + cashOut.getStatus());

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  TRANSACTION DETAILS");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50.50s │%n", "  Vendor: " + vendor.getFirstName() + " " + vendor.getLastName());
        System.out.printf("│ %-50.50s │%n", "  Store: " + vendor.getStore().getStoreName());
        System.out.printf("│ %-50.50s │%n", "  Processed By: " + cashier.getFirstName() + " " + cashier.getLastName());

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50.50s │%n", "  TOTAL CASH-OUT: " + String.format("PHP %,.2f", cashOut.getAmount()));
        System.out.printf("│ %-50.50s │%n", "  Store Balance: " + String.format("PHP %,.2f", cashOut.getStoreWallet().getBalance()));
        System.out.printf("│ %-50s │%n", "");

        System.out.println("└────────────────────────────────────────────────────┘");


        ConsoleUi.pause(sc);
    }


    private static void showTopUpHistory(Cashier cashier) {
        ConsoleUi.showSection("TOP UP RECORDS");

        boolean found = false;

        for (TopUp topUp : DataStore.getTopUps()) {
            if (topUp.getCashier() == cashier) {

                Student student = topUp.getStudent();

                System.out.println();
                System.out.println("┌────────────────────────────────────────────────────┐");
                System.out.printf("│ %-50s │%n", "                   TOP-UP RECORD");
                System.out.println("├────────────────────────────────────────────────────┤");

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50.50s │%n", "  Transaction ID: " + topUp.getTopUpId());
                System.out.printf("│ %-50.50s │%n", "  Date: " + ConsoleUi.formatDateTime(topUp.getDateTime()));
                System.out.printf("│ %-50.50s │%n", "  Status: " + topUp.getStatus());

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50s │%n", "  STUDENT INFORMATION");
                System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

                System.out.printf("│ %-50.50s │%n", "  Student: " + student.getFirstName() + " " + student.getLastName());
                System.out.printf("│ %-50.50s │%n", "  Student Number: " + student.getStudentNumber());
                System.out.printf("│ %-50.50s │%n", "  Handled By: " + cashier.getFirstName() + " " + cashier.getLastName());

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));
                System.out.printf("│ %-50.50s │%n", "  TOP-UP AMOUNT: " + String.format("PHP %,.2f", topUp.getAmount()));
                System.out.printf("│ %-50s │%n", "");

                System.out.println("└────────────────────────────────────────────────────┘");

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

                Vendor vendor = cashOut.getVendor();

                System.out.println();
                System.out.println("┌────────────────────────────────────────────────────┐");
                System.out.printf("│ %-50s │%n", "                  CASH-OUT RECORD");
                System.out.println("├────────────────────────────────────────────────────┤");

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50.50s │%n", "  Transaction ID: " + cashOut.getCashOutId());
                System.out.printf("│ %-50.50s │%n", "  Date: " + ConsoleUi.formatDateTime(cashOut.getDateTime()));
                System.out.printf("│ %-50.50s │%n", "  Status: " + cashOut.getStatus());

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50s │%n", "  VENDOR INFORMATION");
                System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

                System.out.printf("│ %-50.50s │%n", "  Vendor: " + vendor.getFirstName() + " " + vendor.getLastName());
                System.out.printf("│ %-50.50s │%n", "  Store: " + vendor.getStore().getStoreName());
                System.out.printf("│ %-50.50s │%n", "  Handled By: " + cashier.getFirstName() + " " + cashier.getLastName());

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));
                System.out.printf("│ %-50.50s │%n", "  CASH-OUT AMOUNT: " + String.format("PHP %,.2f", cashOut.getAmount()));
                System.out.printf("│ %-50s │%n", "");

                System.out.println("└────────────────────────────────────────────────────┘");

                found = true;
            }
        }

        if (!found) {
            ConsoleUi.showInfo("No cash out records found.");
        }
    }



    private static void searchStudentTopUpRecords(Scanner sc) {
        ConsoleUi.showSection("SEARCH STUDENT TOP UP RECORDS");

        System.out.print("Student Number: ");
        String studentNumber = sc.nextLine();

        Student student = DataStore.findStudentByStudentNumber(studentNumber);

        if (student == null) {
            ConsoleUi.showError("Student not found.");
            return;
        }

        boolean found = false;

        for (TopUp topUp : DataStore.getTopUps()) {
            if (topUp.getStudent() == student) {

                System.out.println();
                System.out.println("┌────────────────────────────────────────────────────┐");
                System.out.printf("│ %-50s │%n", "                STUDENT TOP-UP RECORD");
                System.out.println("├────────────────────────────────────────────────────┤");

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50.50s │%n", "  Student Number: " + student.getStudentNumber());
                System.out.printf("│ %-50.50s │%n", "  Transaction ID: " + topUp.getTopUpId());
                System.out.printf("│ %-50.50s │%n", "  Date: " + ConsoleUi.formatDateTime(topUp.getDateTime()));
                System.out.printf("│ %-50.50s │%n", "  Status: " + topUp.getStatus());

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50s │%n", "  TRANSACTION DETAILS");
                System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

                System.out.printf("│ %-50.50s │%n", "  Handled By: " + topUp.getCashier().getFirstName() + " " + topUp.getCashier().getLastName());

                System.out.printf("│ %-50s │%n", "");
                System.out.printf("│ %-50.50s │%n", "  TOP-UP AMOUNT: " + String.format("PHP %,.2f", topUp.getAmount()));
                System.out.printf("│ %-50s │%n", "");

                System.out.println("└────────────────────────────────────────────────────┘");

                found = true;
            }
        }

        if (!found) {
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

        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "                TRANSACTION SUMMARY");
        System.out.println("├────────────────────────────────────────────────────┤");

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50.50s │%n", "  Cashier: " + cashier.getFirstName() + " " + cashier.getLastName());
        System.out.printf("│ %-50s │%n", "");

        // Top-Up Summary
        System.out.printf("│ %-50s │%n", "  TOP-UP SUMMARY");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50.50s │%n", "  Successful Transactions: " + successfulTopUps);
        System.out.printf("│ %-50.50s │%n", "  Failed Transactions: " + failedTopUps);
        System.out.printf("│ %-50.50s │%n", "  Total Top-Up Amount: " + String.format("PHP %,.2f", totalTopUpAmount));

        System.out.printf("│ %-50s │%n", "");

        // Cash-Out Summary
        System.out.printf("│ %-50s │%n", "  CASH-OUT SUMMARY");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50.50s │%n", "  Successful Transactions: " + successfulCashOuts);
        System.out.printf("│ %-50.50s │%n", "  Failed Transactions: " + failedCashOuts);
        System.out.printf("│ %-50.50s │%n", "  Total Cash-Out Amount: " + String.format("PHP %,.2f", totalCashOutAmount));

        System.out.printf("│ %-50s │%n", "");
        System.out.println("└────────────────────────────────────────────────────┘");
    }

    private static void replaceStudentCard(Scanner sc) {
        ConsoleUi.showSection("REPLACE STUDENT CARD");

        System.out.print("Student Number: ");
        String studentNumber = sc.nextLine();

        Student student = DataStore.findStudentByStudentNumber(studentNumber);

        if (student == null) {
            ConsoleUi.showError("Student not found.");
            return;
        }

        Card lostCard = null;

        for (Card card : DataStore.getCards()) {
            if (card.getStudent() == student && card.getStatus() == CardStatus.LOST) {
                lostCard = card;
                break;
            }
        }

        if (lostCard == null) {
            ConsoleUi.showError("No lost card found for this student.");
            return;
        }

        System.out.print("New Card UID: ");
        String newCardUid = sc.nextLine().trim();

        if (newCardUid.isEmpty()) {
            ConsoleUi.showError("Card UID cannot be empty.");
            return;
        }

        if (DataStore.findCardByUid(newCardUid) != null) {
            ConsoleUi.showError("Card UID already exists.");
            return;
        }

        ConsoleUi.showSection("CONFIRM CARD REPLACEMENT");

        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "           CARD REPLACEMENT CONFIRMATION");
        System.out.println("├────────────────────────────────────────────────────┤");

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  STUDENT INFORMATION");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50.50s │%n", "  Student: " + student.getFirstName() + " " + student.getLastName());
        System.out.printf("│ %-50.50s │%n", "  Student Number: " + student.getStudentNumber());

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  CARD INFORMATION");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50.50s │%n", "  Old Card UID: " + lostCard.getCardUid());
        System.out.printf("│ %-50.50s │%n", "  Old Card Status: " + lostCard.getStatus());
        System.out.printf("│ %-50.50s │%n", "  New Card UID: " + newCardUid);

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  WALLET INFORMATION");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50.50s │%n", "  Wallet Balance: " + String.format("PHP %,.2f", student.getStudentWallet().getBalance()));

        System.out.printf("│ %-50s │%n", "");
        System.out.println("└────────────────────────────────────────────────────┘");


        if (!ConsoleUi.confirmTransaction(sc)) {
            ConsoleUi.showInfo("Card replacement cancelled.");
            return;
        }

        lostCard.markReplaced();

        Card newCard = new Card(newCardUid, student);
        DataStore.addCard(newCard);

        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "               CARD REPLACEMENT RESULT");
        System.out.println("├────────────────────────────────────────────────────┤");

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  STATUS: SUCCESSFUL");
        System.out.printf("│ %-50s │%n", "");

        System.out.printf("│ %-50.50s │%n", "  Old Card UID: " + lostCard.getCardUid());
        System.out.printf("│ %-50.50s │%n", "  Old Card Status: " + lostCard.getStatus());

        System.out.printf("│ %-50s │%n", "");

        System.out.printf("│ %-50.50s │%n", "  New Card UID: " + newCard.getCardUid());
        System.out.printf("│ %-50.50s │%n", "  New Card Status: " + newCard.getStatus());

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  " + "─".repeat(48));

        System.out.printf("│ %-50.50s │%n", "  Wallet Balance: " + String.format("PHP %,.2f", student.getStudentWallet().getBalance()));

        System.out.printf("│ %-50s │%n", "");
        System.out.printf("│ %-50s │%n", "  Existing wallet balance has been preserved.");
        System.out.printf("│ %-50s │%n", "");

        System.out.println("└────────────────────────────────────────────────────┘");
    }
}
