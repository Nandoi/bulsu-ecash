package entities.user;

public class Cashier extends User{
    public Cashier(int userId,
        String firstName,
        String middleName,
        String lastName,
        String contactNumber,
        String email
    ){
        super(userId, firstName, middleName, lastName, contactNumber, email);
    }
}