package entities.user;

import entities.Store;

public class Vendor extends User{
    private Store store;

    public Vendor(
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
        String zipCode,
        Store store
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

        this.store = store;
    }

    @Override
    public String getUserType(){
        return "VENDOR";
    }

    public Store getStore(){
        return store;
    }
}