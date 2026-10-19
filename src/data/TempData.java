package data;

import entities.Account;
import entities.Card;
import entities.Store;
import entities.user.Cashier;
import entities.user.Student;
import entities.user.Vendor;
import entities.wallet.StoreWallet;
import entities.wallet.StudentWallet;

public class TempData {
    private static boolean loaded = false;

    public static void load() {
        if (loaded) {
            return;
        }
        
        // STUDENT
        StudentWallet studentWallet = new StudentWallet(101);
        studentWallet.addBalance(500.00);

        Student student = new Student(
            1001,
            "Fernando",
            "Vargas",
            "Vidal",
            "09123456789",
            "fernando@email.com",
            "2025004302",
            "BSIT",
            2,
            studentWallet
        );

        Card card = new Card(
            301,
            "CARD-001",
            student
        );


        // VENDOR AND STORE
        StoreWallet storeWallet = new StoreWallet(201);

        Store store = new Store(
            1,
            "BulSU Canteen",
            "Food",
            "Main Campus",
            storeWallet
        );

        Vendor vendor = new Vendor(
            2001,
            "Juan",
            "D.",
            "Santos",
            "09999999999",
            "juan@email.com",
            store
        );


        // CASHIER
        Cashier cashier = new Cashier(
            3001,
            "Maria",
            "A.",
            "Cruz",
            "09888888888",
            "maria@email.com"
        );

        Account studentAccount = new Account(DataStore.generateNextAccountId(), "1124", student);
        Account vendorAccount = new Account(DataStore.generateNextAccountId(), "2222", vendor);
        Account cashierAccount = new Account(DataStore.generateNextAccountId(), "3333", cashier);

        
        
        // SAVING OF TEMPORARY DATA
        DataStore.addAuthorizedStudent(student);
        DataStore.addAuthorizedVendor(vendor);
        DataStore.addAuthorizedCashier(cashier);
        DataStore.addCard(card);
        
        DataStore.addAccount(studentAccount);
        DataStore.addAccount(vendorAccount);
        DataStore.addAccount(cashierAccount);
        loaded = true;
    }
}
