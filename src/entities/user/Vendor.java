package entities.user;

public class Vendor extends User{
    Vendor(int userId,
        String firstName,
        String middleName,
        String lastName,
        String contactNumber,
        String email
    ){
        super(userId, firstName, middleName, lastName, contactNumber, email);
    }
}