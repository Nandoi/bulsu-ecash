package services;

import data.DataStore;
import entities.transaction.TopUp;

import entities.user.Cashier;
import entities.user.Student;

import entities.wallet.StudentWallet;

public class TopUpService {

    public static TopUp processTopUp(int topUpId, double amount, String dateTime, String studentNumber, Cashier cashier) {

        // Invalid amount
        if (amount <= 0) {
            return null;
        }

        // TopUp ID must be unique
        if (DataStore.findTopUpById(topUpId) != null) {
            return null;
        }

        // Cashier must exist
        if (cashier == null) {
            return null;
        }

        // Find student
        Student student = DataStore.findStudentByStudentNumber(studentNumber);

        if (student == null) {
            return null;
        }

        // Get Student Wallet
        StudentWallet studentWallet = student.getStudentWallet();

        if (studentWallet == null) {
            return null;
        }

        // Create TopUp record
        TopUp topUp = new TopUp(topUpId, amount, dateTime, cashier, studentWallet);

        // Wallet must be active
        if (!studentWallet.isActive()) {
            topUp.markFailed();
            DataStore.addTopUp(topUp);

            return topUp;
        }

        // Add funds
        boolean added = studentWallet.addBalance(amount);

        if (!added) {
            topUp.markFailed();
            DataStore.addTopUp(topUp);

            return topUp;
        }

        topUp.markSuccessful();

        DataStore.addTopUp(topUp);

        return topUp;
    }
}