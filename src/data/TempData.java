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

        // =========================
        // STUDENTS
        // =========================

        // Student 1
        Student student1 = new Student(
                1001,
                "Fernando",
                "Vargas",
                "Vidal",
                "09171234567",
                "fernandoiiividal@email.com",
                "1 Iris Street St. Michael Homes",
                "Lias",
                "Marilao",
                "Bulacan",
                "3019",
                "2025004302",
                "BSIT",
                2
        );

        StudentWallet studentWallet1 = new StudentWallet(DataStore.generateNextStudentWalletId());

        student1.assignStudentWallet(studentWallet1);

        studentWallet1.addBalance(500);

        Card card1 = new Card(DataStore.generateNextCardUid(), student1);

        Account studentAccount1 = new Account(DataStore.generateNextAccountId(), "1124", student1);


        // Student 2
        Student student2 = new Student(
                1002,
                "Andrea",
                "Santos",
                "Reyes",
                "09181234567",
                "andrea.reyes@email.com",
                "45 Rizal Street",
                "Poblacion",
                "Malolos",
                "Bulacan",
                "3000",
                "2025004303",
                "BSIT",
                2
        );

        StudentWallet studentWallet2 = new StudentWallet(DataStore.generateNextStudentWalletId());

        student2.assignStudentWallet(studentWallet2);
        
        studentWallet2.addBalance(500);

        Card card2 = new Card(DataStore.generateNextCardUid(), student2);

        Account studentAccount2 = new Account(DataStore.generateNextAccountId(), "2222", student2);


        // Student 3
        Student student3 = new Student(
                1003,
                "Joshua",
                "Cruz",
                "Mendoza",
                "09191234567",
                "joshua.mendoza@email.com",
                "78 Bonifacio Street",
                "Tabang",
                "Guiguinto",
                "Bulacan",
                "3015",
                "2025004304",
                "BSIT",
                2
        );

        // Student 4
        Student student4 = new Student(
                1004,
                "Angela",
                "Dela Cruz",
                "Garcia",
                "09201234567",
                "angela.garcia@email.com",
                "22 Luna Street",
                "Sta. Clara",
                "Santa Maria",
                "Bulacan",
                "3022",
                "2025004305",
                "BSIT",
                2
        );


        // Student 5
        Student student5 = new Student(
                1005,
                "Miguel",
                "Lopez",
                "Torres",
                "09211234567",
                "miguel.torres@email.com",
                "90 Del Pilar Street",
                "Banga",
                "Meycauayan",
                "Bulacan",
                "3020",
                "2025004306",
                "BSIT",
                2
        );


        // =========================
        // STORES AND VENDORS
        // =========================

        // Store 1
        StoreWallet storeWallet1 = new StoreWallet(201);
        storeWallet1.addBalance(3000);

        Store store1 = new Store(
                1,
                "BulSU Canteen",
                "Food",
                "Main Campus",
                storeWallet1
        );

        Vendor vendor1 = new Vendor(
                2001,
                "Juan",
                "Dela Cruz",
                "Santos",
                "09175551234",
                "juan.santos@email.com",
                "25 MacArthur Highway",
                "Guinhawa",
                "Malolos",
                "Bulacan",
                "3000",
                store1
        );


        // Store 2
        StoreWallet storeWallet2 = new StoreWallet(202);
        storeWallet2.addBalance(1500);

        Store store2 = new Store(
                2,
                "BulSU Print Shop",
                "Printing",
                "Main Campus",
                storeWallet2
        );

        Vendor vendor2 = new Vendor(
                2002,
                "Carlos",
                "Reyes",
                "Mendoza",
                "09178881234",
                "carlos.mendoza@email.com",
                "17 Rizal Street",
                "Sumapang Matanda",
                "Malolos",
                "Bulacan",
                "3000",
                store2
        );


        // =========================
        // CASHIERS
        // =========================

        Cashier cashier1 = new Cashier(
                3001,
                "Maria",
                "Lopez",
                "Cruz",
                "09176661234",
                "maria.cruz@email.com",
                "88 Mabini Street",
                "Longos",
                "Malolos",
                "Bulacan",
                "3000"
        );

        Cashier cashier2 = new Cashier(
                3002,
                "Paolo",
                "Santos",
                "Garcia",
                "09179991234",
                "paolo.garcia@email.com",
                "55 Bonifacio Street",
                "Mojon",
                "Malolos",
                "Bulacan",
                "3000"
        );


        // =========================
        // PRE-CREATED ACCOUNTS
        // =========================

        Account vendorAccount1 = new Account(DataStore.generateNextAccountId(), "3333", vendor1);

        Account cashierAccount1 = new Account(DataStore.generateNextAccountId(), "4444", cashier1);


        // =========================
        // SAVE TEMPORARY DATA
        // =========================

        // Authorized students
        DataStore.addAuthorizedStudent(student1);
        DataStore.addAuthorizedStudent(student2);
        DataStore.addAuthorizedStudent(student3);
        DataStore.addAuthorizedStudent(student4);
        DataStore.addAuthorizedStudent(student5);

        // Student cards
        DataStore.addCard(card1);
        DataStore.addCard(card2);

        // Authorized vendors
        DataStore.addAuthorizedVendor(vendor1);
        DataStore.addAuthorizedVendor(vendor2);

        // Authorized cashiers
        DataStore.addAuthorizedCashier(cashier1);
        DataStore.addAuthorizedCashier(cashier2);

        // Existing accounts
        DataStore.addAccount(studentAccount1);
        DataStore.addAccount(studentAccount2);
        DataStore.addAccount(vendorAccount1);
        DataStore.addAccount(cashierAccount1);

        loaded = true;
    }
}