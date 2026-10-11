package datas;

import java.util.ArrayList;

import entities.Account;
import entities.user.Cashier;
import entities.user.Student;
import entities.user.Vendor;

public class DataStore {
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static ArrayList<Student> authorizedStudents = new ArrayList<>();
    private static ArrayList<Vendor> authorizedVendors = new ArrayList<>();
    private static ArrayList<Cashier> authorizedCashiers = new ArrayList<>();

    // ACCOUNT

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
}
