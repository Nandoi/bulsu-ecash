package ui;

public class ConsoleUi {
    private static final String LINE = "============================================================";

    public static void showTitle() {
        System.out.println();
        System.out.println(LINE);
        System.out.println("                       BulSU eCash");
        System.out.println("              Campus Cashless Payment System");
        System.out.println(LINE);
    }

    public static void showSection(String title) {
        System.out.println();
        System.out.println(LINE);
        System.out.println("  " + title);
        System.out.println(LINE);
    }

    public static void showOption(int number, String text) {
        System.out.println("  [" + number + "] " + text);
    }

    public static void showExitOption(String text) {
        System.out.println("  [0] " + text);
    }

    public static void showSuccess(String message) {
        System.out.println();
        System.out.println("[SUCCESS] " + message);
    }

    public static void showError(String message) {
        System.out.println();
        System.out.println("[ERROR] " + message);
    }

    public static void showInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    public static void showBalance(String label, double amount) {
        System.out.printf("%s: PHP %.2f%n", label, amount);
    }
}