import entities.Card;
import entities.Store;
import entities.user.Student;
import entities.user.Vendor;
import entities.wallet.StudentWallet;
import entities.wallet.StoreWallet;

public class Main {

    public static void main(String[] args) {

        StudentWallet studentWallet = new StudentWallet(101);

        Student student = new Student(
            1,
            "Fernando",
            "V.",
            "Vidal",
            "09123456789",
            "fernando@email.com",
            "2025-001234",
            "BSIT",
            2,
            studentWallet
        );

        Card card = new Card(
            1,
            "04A1B29C77",
            student
        );

        StoreWallet storeWallet = new StoreWallet(201);

        Store store = new Store(
            1,
            "Campus Chicken",
            "Food",
            "Main Campus",
            storeWallet
        );

        Vendor vendor = new Vendor(
            2,
            "Juan",
            "D.",
            "Santos",
            "09999999999",
            "juan@email.com",
            store
        );
        
        //Pang test lang ng relation, so working na ang card to get student to get studentwallet
        //also working na ang vendor to access store wallet.
        System.out.println(card.getStudent().getFirstName() + " " + student.getLastName());
        System.out.println(card.getStudent().getStudentWallet().getBalance());

        System.out.println(vendor.getStore().getStoreName());
        System.out.println(vendor.getStore().getStoreWallet().getBalance());
    }
}