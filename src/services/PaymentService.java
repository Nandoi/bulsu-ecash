package services;

import data.DataStore;
import entities.Card;

import entities.transaction.Payment;

import entities.user.Student;
import entities.user.Vendor;

import entities.wallet.StudentWallet;
import entities.wallet.StoreWallet;

public class PaymentService {

    public static Payment processPayment(int paymentId, double amount, String dateTime, String cardUid, Vendor vendor) {
        // Invalid amount
        if (amount <= 0){
            return null;
        }

        // Payment ID must be unique
        if (DataStore.findPaymentById(paymentId) != null) {
            return null;
        }

        // Find card
        Card card = DataStore.findCardByUid(cardUid);

        if (card == null){
            return null;
        }

        // Card cannot be LOST, REPLACED or EXPIRED
        if (!card.isActive()){
            return null;
        }

        // Find student connected to card
        Student student = card.getStudent();

        if (student == null){
            return null;
        }

        StudentWallet studentWallet = student.getStudentWallet();

        if (studentWallet == null){
            return null;
        }


        // Vendor must have a Store
        if (vendor == null || vendor.getStore() == null){
            return null;
        }


        StoreWallet storeWallet = vendor.getStore().getStoreWallet();

        if (storeWallet == null){
            return null;
        }


        // Create transaction record
        Payment transaction = new Payment(paymentId, amount, dateTime, card, studentWallet, vendor, storeWallet);

        // Wallets must be active
        if (!studentWallet.isActive() || !storeWallet.isActive()) {
            transaction.markFailed();
            DataStore.addPayment(transaction);

            return transaction;
        }

        // Try deducting from student
        boolean deducted = studentWallet.deductBalance(amount);

        if (!deducted) {
            transaction.markFailed();
            DataStore.addPayment(transaction);

            return transaction;
        }

        // Credit store wallet
        boolean added = storeWallet.addBalance(amount);

        if (!added) {
            // Return money to student
            studentWallet.addBalance(amount);

            transaction.markFailed();
            DataStore.addPayment(transaction);

            return transaction;
        }


        transaction.markSuccessful();

        DataStore.addPayment(transaction);

        return transaction;
    }
}