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
                "Capelo",
                "Del Carmen",
                "09201234567",
                "joshuadelcarmen@email.com",
                "90 Del Pilar Street",
                "Banga",
                "Meycauayan",
                "Bulacan",
                "3020",
                "2025004209",
                "BSIT",
                2
        );

        Student student4 = new Student(
                1004,
                "Maam",
                "Robethel",
                "Andres",
                "09953095621",
                "maambetong@email.com",
                "23 Walisan",
                "Loma de Gato",
                "Guiguinto",
                "Bulacan",
                "3005",
                "2025005050",
                "BSIT",
                4
        );

        Student student5 = new Student(
                1005,
                "Jason",
                "Vorhees",
                "Di",
                "09234312518",
                "jasondi@email.com",
                "1 Insulator",
                "Lias",
                "Marilao",
                "Bulacan",
                "3019",
                "2025001234",
                "BSIT",
                2
        );


        // =========================
        // STORES AND VENDORS
        // =========================

        // =========================
        // VENDOR 1 - REGISTERED
        // =========================

        // Create authorized Vendor first
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
                null
        );

        // Existing Store Wallet
        StoreWallet storeWallet1 = new StoreWallet(201);
        storeWallet1.addBalance(3000);

        // Existing Store
        Store store1 = new Store(
                1,
                "BulSU Canteen",
                "Food",
                "Main Campus",
                storeWallet1
        );

        // Assign Store to registered Vendor
        vendor1.assignStore(store1);

        Account vendorAccount1 = new Account(
                DataStore.generateNextAccountId(),
                "3333",
                vendor1
        );

        // =========================
        // VENDOR 2 - NOT REGISTEREDs
        // =========================

        // Authorized Vendor without Account or Store
        Vendor vendor2 = new Vendor(
                2002,
                "Jullian",
                "Reyes",
                "Manarang",
                "09178881234",
                "julian.mendoza@email.com",
                "17 Rizal Street",
                "Sumapang Matanda",
                "Malolos",
                "Bulacan",
                "3000",
                null
        );

        // Vendor 2 will receive a Store and Store Wallet
        // only after successful eCash registration.


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

        Account cashierAccount1 = new Account(DataStore.generateNextAccountId(), "4444", cashier1);

        Cashier cashier2 = new Cashier(
                3002,
                "Michelle",
                "Eugenio",
                "Jumaquio",
                "09211234567",
                "michellejumaquio@email.com",
                "12 Kawayan",
                "Loma de Gato",
                "Plaridel",
                "Bulacan",
                "3020"
        );




        // =========================
        // PRE-CREATED ACCOUNTS
        // =========================


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