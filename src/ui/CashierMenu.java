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
            showMenu(cashier);
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
                    showTransactionSummary(cashier);
                    ConsoleUi.pause(sc);
                    break;

                case 6:
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
        showTopUpConfirmation(student, cashier, amount);

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

        showTopUpReceipt(topUp);
        ConsoleUi.pause(sc);
    }

    private static void showTopUpConfirmation(Student student, Cashier cashier, double amount) {

        ConsoleUi.boxHeader("TOP-UP CONFIRMATION");

        // Student information
        ConsoleUi.boxLine("  Student: " + student.getFirstName()
                + " " + student.getLastName());

        ConsoleUi.boxLine("  Student No.: " + student.getStudentNumber());

        ConsoleUi.boxLine("  Handled By: " + cashier.getFirstName()
                + " " + cashier.getLastName());

        ConsoleUi.boxDivider();

        // Balance information
        double currentBalance = student.getStudentWallet().getBalance();
        double newBalance = currentBalance + amount;

        ConsoleUi.boxLine("  Current Balance: "
                + String.format("PHP %,.2f", currentBalance));

        ConsoleUi.boxLine("  Top-Up Amount: "
                + String.format("PHP %,.2f", amount));

        ConsoleUi.boxLine("  New Balance: "
                + String.format("PHP %,.2f", newBalance));

        ConsoleUi.boxFooter();
    }

    private static void showTopUpReceipt(TopUp topUp) {
        Student student = topUp.getStudent();
        Cashier cashier = topUp.getCashier();

        ConsoleUi.boxHeader("TOP-UP RECEIPT");

        // Transaction details
        ConsoleUi.boxLine("  Status: " + topUp.getStatus());
        ConsoleUi.boxLine("  Transaction ID: " + topUp.getTopUpId());

        ConsoleUi.boxLine("  Date: "
                + ConsoleUi.formatDateTime(topUp.getDateTime()));

        ConsoleUi.boxDivider();

        // Student and cashier
        ConsoleUi.boxLine("  Student: " + student.getFirstName()
                + " " + student.getLastName());

        ConsoleUi.boxLine("  Student No.: " + student.getStudentNumber());

        ConsoleUi.boxLine("  Processed By: " + cashier.getFirstName()
                + " " + cashier.getLastName());

        ConsoleUi.boxDivider();

        // Final amounts
        ConsoleUi.boxLine("  AMOUNT LOADED: "
                + String.format("PHP %,.2f", topUp.getAmount()));

        ConsoleUi.boxLine("  New Balance: "
                + String.format("PHP %,.2f",
                        topUp.getStudentWallet().getBalance()));

        ConsoleUi.boxFooter();
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

        if (vendor.getStore() == null) {
        ConsoleUi.showError("Vendor has no assigned store.");
        ConsoleUi.pause(sc);
        return;
        }

        if (vendor.getStore().getStoreWallet() == null) {
            ConsoleUi.showError("Vendor store has no wallet.");
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
        showCashOutConfirmation(vendor, cashier, amount);

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

        showCashOutReceipt(cashOut);
        ConsoleUi.pause(sc);
    }

    private static void showCashOutConfirmation(Vendor vendor, Cashier cashier, double amount) {
        double currentBalance = vendor.getStore().getStoreWallet().getBalance();

        ConsoleUi.boxHeader("CASH-OUT CONFIRMATION");

        // Vendor information
        ConsoleUi.boxLine("  Vendor: " + vendor.getFirstName() + " " + vendor.getLastName());

        ConsoleUi.boxLine("  Vendor ID: " + vendor.getUserId());

        ConsoleUi.boxLine("  Store: " + vendor.getStore().getStoreName());

        ConsoleUi.boxLine("  Handled By: " + cashier.getFirstName()
                + " " + cashier.getLastName());

        ConsoleUi.boxDivider();

        // Cash-out information
        ConsoleUi.boxLine("  Current Balance: "
                + String.format("PHP %,.2f", currentBalance));

        ConsoleUi.boxLine("  Cash-Out Amount: "
                + String.format("PHP %,.2f", amount));

        if (amount <= currentBalance) {
            double newBalance = currentBalance - amount;

            ConsoleUi.boxLine("  Balance if Successful: "
                    + String.format("PHP %,.2f", newBalance));
        } else {
            ConsoleUi.boxLine("  Balance if Successful: N/A");
            ConsoleUi.boxLine("  WARNING: Insufficient store funds.");
        }

        ConsoleUi.boxFooter();
    }

    private static void showCashOutReceipt(CashOut cashOut) {
        Vendor vendor = cashOut.getVendor();
        Cashier cashier = cashOut.getCashier();

        ConsoleUi.boxHeader("CASH-OUT RECEIPT");

        // Transaction information
        ConsoleUi.boxLine("  Status: " + cashOut.getStatus());

        ConsoleUi.boxLine("  Transaction ID: "
                + cashOut.getCashOutId());

        ConsoleUi.boxLine("  Date: "
                + ConsoleUi.formatDateTime(cashOut.getDateTime()));

        ConsoleUi.boxDivider();

        // Vendor and cashier information
        ConsoleUi.boxLine("  Vendor: " + vendor.getFirstName()
                + " " + vendor.getLastName());

        ConsoleUi.boxLine("  Store: "
                + vendor.getStore().getStoreName());

        ConsoleUi.boxLine("  Processed By: "
                + cashier.getFirstName()
                + " " + cashier.getLastName());

        ConsoleUi.boxDivider();

        // Final transaction amounts
        ConsoleUi.boxLine("  AMOUNT WITHDRAWN: "
                + String.format("PHP %,.2f", cashOut.getAmount()));

        ConsoleUi.boxLine("  Remaining Balance: "
                + String.format("PHP %,.2f",
                        cashOut.getStoreWallet().getBalance()));

        ConsoleUi.boxFooter();
    }

    private static void showTopUpHistory(Cashier cashier) {
        ConsoleUi.boxHeader("TOP-UP RECORDS");

        ConsoleUi.boxLine("  Cashier: " + cashier.getFirstName()
                + " " + cashier.getLastName());

        ConsoleUi.boxDivider();

        boolean found = false;

        for (TopUp topUp : DataStore.getTopUps()) {
            if (topUp.getCashier() == cashier) {
                if (found) {
                    ConsoleUi.boxRecordDivider();
                }

                showTopUpRecord(topUp);
                found = true;
            }
        }

        if (!found) {
            ConsoleUi.boxLine("  No top-up records found.");
        }

        ConsoleUi.boxFooter();
    }

    private static void showTopUpRecord(TopUp topUp) {
        Student student = topUp.getStudent();

        ConsoleUi.boxLine("  #" + topUp.getTopUpId()
                + " | " + topUp.getStatus());

        ConsoleUi.boxLine("  "
                + ConsoleUi.formatDateTime(topUp.getDateTime()));

        ConsoleUi.boxLine("  Student: " + student.getFirstName()
                + " " + student.getLastName());

        ConsoleUi.boxLine("  Student No.: "
                + student.getStudentNumber());

        if (topUp.getStatus() == TransactionStatus.SUCCESSFUL) {
            ConsoleUi.boxLine("  TOP-UP: "
                    + String.format("PHP %,.2f", topUp.getAmount()));
        } else {
            ConsoleUi.boxLine("  ATTEMPTED: "
                    + String.format("PHP %,.2f", topUp.getAmount()));
        }
    }

    private static void showCashOutHistory(Cashier cashier) {
        ConsoleUi.boxHeader("CASH-OUT RECORDS");

        ConsoleUi.boxLine("  Cashier: " + cashier.getFirstName()
                + " " + cashier.getLastName());

        ConsoleUi.boxDivider();

        boolean found = false;

        for (CashOut cashOut : DataStore.getCashOuts()) {
            if (cashOut.getCashier() == cashier) {
                if (found) {
                    ConsoleUi.boxRecordDivider();
                }

                showCashOutRecord(cashOut);
                found = true;
            }
        }

        if (!found) {
            ConsoleUi.boxLine("  No cash-out records found.");
        }

        ConsoleUi.boxFooter();
    }

    private static void showCashOutRecord(CashOut cashOut) {
        Vendor vendor = cashOut.getVendor();

        ConsoleUi.boxLine("  #" + cashOut.getCashOutId()
                + " | " + cashOut.getStatus());

        ConsoleUi.boxLine("  "
                + ConsoleUi.formatDateTime(cashOut.getDateTime()));

        ConsoleUi.boxLine("  Vendor: " + vendor.getFirstName()
                + " " + vendor.getLastName());

        ConsoleUi.boxLine("  Store: "
                + vendor.getStore().getStoreName());

        if (cashOut.getStatus() == TransactionStatus.SUCCESSFUL) {
            ConsoleUi.boxLine("  CASH-OUT: "
                    + String.format("PHP %,.2f", cashOut.getAmount()));
        } else {
            ConsoleUi.boxLine("  ATTEMPTED: "
                    + String.format("PHP %,.2f", cashOut.getAmount()));
        }
    }

    private static void showTransactionSummary(Cashier cashier) {
        int successfulTopUps = 0;
        int failedTopUps = 0;
        double totalTopUpAmount = 0;

        int successfulCashOuts = 0;
        int failedCashOuts = 0;
        double totalCashOutAmount = 0;

        // Count transactions handled by this cashier
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

        // Overall totals
        int totalSuccessful = successfulTopUps + successfulCashOuts;
        int totalFailed = failedTopUps + failedCashOuts;
        int totalTransactions = totalSuccessful + totalFailed;

        // Display report
        ConsoleUi.boxHeader("TRANSACTION SUMMARY");
        ConsoleUi.boxLine("  Cashier: " + cashier.getFirstName() + " " + cashier.getLastName());
        ConsoleUi.boxLine("  Cashier ID: " + cashier.getUserId());
        ConsoleUi.boxDivider();

        // Top-up summary
        ConsoleUi.boxLine("  TOP-UP SUMMARY");
        ConsoleUi.boxLine("  Successful: " + successfulTopUps + "  |  Failed: " + failedTopUps);
        ConsoleUi.boxLine("  Total Loaded: " + String.format("PHP %,.2f", totalTopUpAmount));
        ConsoleUi.boxDivider();

        // Cash-out summary
        ConsoleUi.boxLine("  CASH-OUT SUMMARY");
        ConsoleUi.boxLine("  Successful: " + successfulCashOuts + "  |  Failed: " + failedCashOuts);
        ConsoleUi.boxLine("  Total Withdrawn: " + String.format("PHP %,.2f", totalCashOutAmount));
        ConsoleUi.boxDivider();

        // Overall summary
        ConsoleUi.boxLine("  OVERALL SUMMARY");
        ConsoleUi.boxLine("  Total Transactions: " + totalTransactions);
        ConsoleUi.boxLine("  Successful: " + totalSuccessful + "  |  Failed: " + totalFailed);
        ConsoleUi.boxLine("  Current session records only");
        ConsoleUi.boxFooter();
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

        System.out.print("New Card UID: CARD-");
        String newCardUid = "CARD-1" + sc.nextLine().trim();

        if (newCardUid.isEmpty()) {
            ConsoleUi.showError("Card UID cannot be empty.");
            return;
        }

        if (DataStore.findCardByUid(newCardUid) != null) {
            ConsoleUi.showError("Card UID already exists.");
            return;
        }

        showCardReplacementConfirmation(student, lostCard, newCardUid);

        if (!ConsoleUi.confirmTransaction(sc)) {
            ConsoleUi.showInfo("Card replacement cancelled.");
            return;
        }

        lostCard.markReplaced();

        Card newCard = new Card(newCardUid, student);
        DataStore.addCard(newCard);

        showCardReplacementResult(student, lostCard, newCard);
    }

    private static void showCardReplacementConfirmation(Student student, Card lostCard, String newCardUid) {
        ConsoleUi.boxHeader("CARD REPLACEMENT CONFIRMATION");

        // Student information
        ConsoleUi.boxLine("  Student: " + student.getFirstName()
                + " " + student.getLastName());

        ConsoleUi.boxLine("  Student No.: "
                + student.getStudentNumber());

        ConsoleUi.boxDivider();

        // Card information
        ConsoleUi.boxLine("  Old Card UID: "
                + lostCard.getCardUid());

        ConsoleUi.boxLine("  Old Card Status: "
                + lostCard.getStatus());

        ConsoleUi.boxLine("  New Card UID: " + newCardUid);

        ConsoleUi.boxDivider();

        // Wallet information
        ConsoleUi.boxLine("  Wallet Balance: "
                + String.format("PHP %,.2f",
                        student.getStudentWallet().getBalance()));

        ConsoleUi.boxLine("  Existing balance will be preserved.");

        ConsoleUi.boxFooter();
    }

    private static void showCardReplacementResult(Student student, Card lostCard, Card newCard) {
        ConsoleUi.boxHeader("CARD REPLACEMENT RESULT");

        ConsoleUi.boxLine("  Status: SUCCESSFUL");

        ConsoleUi.boxLine("  Student: " + student.getFirstName()
                + " " + student.getLastName());

        ConsoleUi.boxDivider();

        // Card information
        ConsoleUi.boxLine("  Old Card UID: "
                + lostCard.getCardUid());

        ConsoleUi.boxLine("  Old Card Status: "
                + lostCard.getStatus());

        ConsoleUi.boxLine("  New Card UID: "
                + newCard.getCardUid());

        ConsoleUi.boxLine("  New Card Status: "
                + newCard.getStatus());

        ConsoleUi.boxDivider();

        // Wallet information
        ConsoleUi.boxLine("  Wallet Balance: "
                + String.format("PHP %,.2f",
                        student.getStudentWallet().getBalance()));

        ConsoleUi.boxLine(
                "  Existing wallet balance has been preserved.");

        ConsoleUi.boxFooter();
    }

    private static void showMenu(Cashier cashier) {
        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │%n", "                  CASHIER DASHBOARD");
        System.out.println("├────────────────────────────────────────────────────┤");

        // Cashier information
        System.out.printf("│ %-50.50s │%n",
                "  " + "Hello, " + cashier.getFirstName() + " " + cashier.getLastName());
        System.out.printf("│ %-50.50s │%n",
                "  Cashier ID: " + cashier.getUserId());

        System.out.println("├────────────────────────────────────────────────────┤");

        // Transaction services
        System.out.printf("│ %-50s │%n", "  TRANSACTION SERVICES");
        System.out.printf("│ %-50s │%n", "  [1] Student Top-Up");
        System.out.printf("│ %-50s │%n", "  [2] Vendor Cash-Out");
        System.out.printf("│ %-50s │%n", "");

        // Records and reports
        System.out.printf("│ %-50s │%n", "  RECORDS & REPORTS");
        System.out.printf("│ %-50s │%n", "  [3] Top-Up Records");
        System.out.printf("│ %-50s │%n", "  [4] Cash-Out Records");
        System.out.printf("│ %-50s │%n", "  [5] Transaction Summary");
        System.out.printf("│ %-50s │%n", "");

        // Card management
        System.out.printf("│ %-50s │%n", "  CARD MANAGEMENT");
        System.out.printf("│ %-50s │%n", "  [6] Replace Lost Card");
        System.out.printf("│ %-50s │%n", "");

        // Logout
        System.out.printf("│ %-50s │%n", "  [0] Logout");

        System.out.println("└────────────────────────────────────────────────────┘");
    }
}
