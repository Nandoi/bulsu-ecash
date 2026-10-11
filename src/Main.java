import datas.DataStore;
import entities.Account;
import entities.user.Student;
import entities.wallet.StudentWallet;

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

        Account account = new Account(
            1001,
            "1234",
            student
        );

        DataStore.addAccount(account);

        Account foundAccount = DataStore.findAccountById(1001);

        System.out.println(foundAccount.getUser().getFirstName());
    }
}