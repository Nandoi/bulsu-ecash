package services;

import data.DataStore;
import entities.transaction.TopUp;

import entities.user.Cashier;
import entities.user.Student;

import entities.wallet.StudentWallet;

public class TopUpService {

    public static TopUp processTopUp(double amount, String studentNumber, Cashier cashier) {

        int topUpId = DataStore.generateNextTopUpId();
        String dateTime = java.time.LocalDateTime.now().toString();

        // Invalid amount
        if (amount <= 0) {
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
        TopUp topUp = new TopUp(topUpId, amount, dateTime, cashier, student, studentWallet);

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