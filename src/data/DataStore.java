package data;

import java.util.ArrayList;

import entities.Account;
import entities.Card;
import entities.transaction.CashOut;
import entities.transaction.TopUp;
import entities.transaction.Payment;
import entities.user.Cashier;
import entities.user.Student;
import entities.user.Vendor;

public class DataStore {
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static ArrayList<Student> authorizedStudents = new ArrayList<>();
    private static ArrayList<Vendor> authorizedVendors = new ArrayList<>();
    private static ArrayList<Cashier> authorizedCashiers = new ArrayList<>();
    private static ArrayList<Card> cards = new ArrayList<>();
    private static ArrayList<Payment> payments = new ArrayList<>();
    private static ArrayList<TopUp> topUps = new ArrayList<>();
    private static ArrayList<CashOut> cashOuts = new ArrayList<>(); 

    // ACCOUNT
    private static int nextAccountId = 5001;

    public static int generateNextAccountId() {
        return nextAccountId++;
    }

    public static void addAccount(Account account) {
        accounts.add(account);
    }

    public static Account findAccountById(int accountId) {

        for (Account account : accounts) {

            if (account.getAccountId() == accountId) {
                return account;
            }
        }
        return null;
    }

    public static Account findAccountByUserId(int userId) {

        for (Account account : accounts) {

            if (account.getUser().getUserId() == userId) {
                return account;
            }
        }
        return null;
    }


    // STUDENT
    private static int nextStudentWalletId = 101;

    public static int generateNextStudentWalletId() {
        return nextStudentWalletId++;
    }

    public static void addAuthorizedStudent(Student student) {
        authorizedStudents.add(student);
    }

    public static Student findStudentByStudentNumber(String studentNumber) {

        for (Student student : authorizedStudents) {

            if (student.getStudentNumber().equals(studentNumber)) {
                return student;
            }
        }

        return null;
    }


    // VENDOR
    public static void addAuthorizedVendor(Vendor vendor) {
        authorizedVendors.add(vendor);
    }

    public static Vendor findVendorByUserId(int userId) {

        for (Vendor vendor : authorizedVendors) {

            if (vendor.getUserId() == userId) {
                return vendor;
            }
        }

        return null;
    }


    // CASHIER
    public static void addAuthorizedCashier(Cashier cashier) {
        authorizedCashiers.add(cashier);
    }

    public static Cashier findCashierByUserId(int userId) {

        for (Cashier cashier : authorizedCashiers) {

            if (cashier.getUserId() == userId) {
                return cashier;
            }
        }

        return null;
    }

    // CARD
    public static ArrayList<Card> getCards() {
        return cards;
    }

    private static int nextCardId = 1;

    public static String generateNextCardUid() {
        return String.format("CARD-%03d", nextCardId++);
    }

    public static void addCard(Card card) {
        cards.add(card);
    }

    public static Card findCardByUid(String cardUid) {

        for (Card card : cards) {

            if (card.getCardUid().equals(cardUid)) {
                return card;
            }
        }

        return null;
    }

    // PAYMENT
    public static ArrayList<Payment> getPayments() {
        return payments;
    }

    private static int nextPaymentId = 6001;

    public static int generateNextPaymentId() {
        return nextPaymentId++;
    }
    
    public static void addPayment(Payment payment) {
        payments.add(payment);
    }

    // TOP UP
    public static ArrayList<TopUp> getTopUps() {
        return topUps;
    }
    
    private static int nextTopUpId = 7001;

    public static int generateNextTopUpId() {
        return nextTopUpId++;
    }

    public static void addTopUp(TopUp topUp) {
        topUps.add(topUp);
    }


    // CASH OUT
    public static ArrayList<CashOut> getCashOuts() {
        return cashOuts;
    }

    private static int nextCashOutId = 8001;

    public static int generateNextCashOutId() {
        return nextCashOutId++;
    }

    public static void addCashOut(CashOut cashOut) {
        cashOuts.add(cashOut);
    }
}
