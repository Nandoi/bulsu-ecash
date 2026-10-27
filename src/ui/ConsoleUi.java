package ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Shared console presentation helpers.
 * Uses plain Unicode box-drawing characters (no external UI dependencies).
 * Keep transaction and authentication logic in the service classes.
 */
public final class ConsoleUi {
    private static final int CONTENT_WIDTH = 54;
    private static final String BORDER = "─".repeat(CONTENT_WIDTH + 2);
    private static final String DIVIDER = "─".repeat(CONTENT_WIDTH);
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a", Locale.ENGLISH);

    private ConsoleUi() {
    }

    private static void printRow(String content) {
        String remaining = content == null ? "" : content;
        if (remaining.isEmpty()) {
            System.out.printf("│ %-" + CONTENT_WIDTH + "s │%n", "");
            return;
        }

        while (remaining.length() > CONTENT_WIDTH) {
            int split = remaining.lastIndexOf(' ', CONTENT_WIDTH);
            if (split < CONTENT_WIDTH / 2) {
                split = CONTENT_WIDTH;
            }
            String part = remaining.substring(0, split).stripTrailing();
            System.out.printf("│ %-" + CONTENT_WIDTH + "s │%n", part);
            remaining = remaining.substring(split).stripLeading();
        }
        System.out.printf("│ %-" + CONTENT_WIDTH + "s │%n", remaining);
    }

    private static void printCentered(String text) {
        String value = text == null ? "" : text;
        int leading = Math.max(0, (CONTENT_WIDTH - value.length()) / 2);
        printRow(" ".repeat(leading) + value);
    }

    public static void showPanel(String title, String... lines) {
        System.out.println();
        System.out.println("┌" + BORDER + "┐");
        printCentered("BulSU eCash");
        printCentered(title);
        System.out.println("├" + BORDER + "┤");
        for (String line : lines) {
            printRow(line);
        }
        System.out.println("└" + BORDER + "┘");
    }

    public static String field(String label, Object value) {
        return String.format("%-21s %s", label, String.valueOf(value));
    }

    public static String money(double amount) {
        return String.format(Locale.US, "PHP %,.2f", amount);
    }

    public static String divider() {
        return DIVIDER;
    }

    public static void showMenu(String title, String[] options, String exitLabel, String... overview) {
        List<String> lines = new ArrayList<>();
        if (overview.length > 0) {
            for (String line : overview) {
                lines.add(line);
            }
            lines.add("");
            lines.add(DIVIDER);
            lines.add("");
        }
        lines.add("OPTIONS");
        lines.add("");
        for (int i = 0; i < options.length; i++) {
            lines.add(String.format("  [%d] %s", i + 1, options[i]));
        }
        lines.add(String.format("  [0] %s", exitLabel));
        lines.add("");
        showPanel(title, lines.toArray(new String[0]));
    }

    public static void showReceipt(
            String type, int transactionId, String dateTime,
            double amount, String status, String... details) {
        List<String> lines = new ArrayList<>();
        lines.add(field("Transaction ID", transactionId));
        lines.add(field("Date", formatDateTime(dateTime)));
        for (String detail : details) {
            lines.add(detail);
        }
        lines.add("");
        lines.add(DIVIDER);
        lines.add(field("TOTAL AMOUNT", money(amount)));
        lines.add(field("STATUS", status));
        showPanel(type + " RECEIPT", lines.toArray(new String[0]));
    }

    public static void showHistoryRecord(String title, String... details) {
        showPanel(title, details);
    }

    public static void showTitle() {
        showPanel("Campus Cashless Payment System");
    }

    public static void showSection(String title) {
        showPanel(title);
    }

    public static void showOption(int number, String text) {
        System.out.printf("  [%d] %s%n", number, text);
    }

    public static void showExitOption(String text) {
        showOption(0, text);
    }

    public static void showSuccess(String message) {
        System.out.println();
        System.out.println("[SUCCESS] — " + message);
    }

    public static void showError(String message) {
        System.out.println();
        System.out.println("[ERROR] — " + message);
    }

    public static void showInfo(String message) {
        System.out.println("[INFO] — " + message);
    }

    public static void showBalance(String label, double amount) {
        System.out.println(field(label, money(amount)));
    }

    public static boolean confirmTransaction(Scanner sc) {
        while (true) {
            showPanel("CONFIRM ACTION", "", "  [1] Confirm", "  [0] Cancel", "");
            int choice = readInt(sc, "Enter Choice: ");
            if (choice == 1) {
                return true;
            }
            if (choice == 0) {
                return false;
            }
            showError("Invalid choice.");
        }
    }

    public static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                showError("Please enter a valid number.");
            }
        }
    }

    public static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            try {
                return Double.parseDouble(input.trim());
            } catch (NumberFormatException e) {
                showError("Please enter a valid amount.");
            }
        }
    }

    public static void pause(Scanner sc) {
        System.out.println();
        System.out.print("Press ENTER to continue...");
        sc.nextLine();
    }

    public static String formatDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime).format(DATE_TIME_FORMATTER);
    }
}
