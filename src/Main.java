import datas.DataStore;
import entities.Account;
import entities.user.Student;
import entities.user.User;
import entities.wallet.StudentWallet;
import services.Authorization;

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

        //
        Account foundAccount = DataStore.findAccountById(1001);

        System.out.println(foundAccount.getUser().getFirstName());

        //
        Account loggedInAccount = Authorization.login(1001, "1234");

        if (loggedInAccount != null) {

            User user = loggedInAccount.getUser();

            String userType = user.getUserType();

            System.out.println("Login successful!");
            System.out.println("User Type: " + userType);

            if (userType.equals("STUDENT")) {

                System.out.println("Student Menu");

            } else if (userType.equals("VENDOR")) {

                System.out.println("Vendor Menu");

            } else if (userType.equals("CASHIER")) {

                System.out.println("Cashier Menu");
            }

        } else {

            System.out.println("Login failed.");
        }
    }
}