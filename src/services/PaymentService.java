package services;

import data.DataStore;
import entities.Card;

import entities.transaction.Payment;

import entities.user.Student;
import entities.user.Vendor;

import entities.wallet.StudentWallet;
import entities.wallet.StoreWallet;

public class PaymentService {

    public static Payment processPayment(double amount, String cardUid, Vendor vendor) {

        int paymentId = DataStore.generateNextPaymentId();
        String dateTime = java.time.LocalDateTime.now().toString();

        // Dapat greater than 0 yung amount na iinput ni vendor.
        if (amount <= 0){
            return null;
        }

        // Find card
        Card card = DataStore.findCardByUid(cardUid);

        if (card == null){
            return null;
        }

        // Card must be ACTIVE
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
        Payment payment = new Payment(paymentId, amount, dateTime, card, studentWallet, vendor, storeWallet);

        // Wallets must be active
        if (!studentWallet.isActive() || !storeWallet.isActive()) {
            payment.markFailed();
            DataStore.addPayment(payment);

            return payment;
        }

        // Try deducting from student
        boolean deducted = studentWallet.deductBalance(amount);

        if (!deducted) {
            payment.markFailed();
            DataStore.addPayment(payment);

            return payment;
        }

        // Credit store wallet
        boolean added = storeWallet.addBalance(amount);

        if (!added) {
            // Return money to student
            studentWallet.addBalance(amount);

            payment.markFailed();
            DataStore.addPayment(payment);

            return payment;
        }


        payment.markSuccessful();

        DataStore.addPayment(payment);

        return payment;
    }
}