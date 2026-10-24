package entities.user;

public class Cashier extends User{
    public Cashier(
        int userId,
        String firstName,
        String middleName,
        String lastName,
        String contactNumber,
        String email,
        String street,
        String barangay,
        String cityMunicipality,
        String province,
        String zipCode
    ){
        super(
            userId,
            firstName,
            middleName,
            lastName,
            contactNumber,
            email,
            street,
            barangay,
            cityMunicipality,
            province,
            zipCode
        );
    }

    @Override
    public String getUserType() {
        return "CASHIER";
    }
}