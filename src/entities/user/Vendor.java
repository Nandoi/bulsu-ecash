package entities.user;

import entities.Store;
public class Vendor extends User{
    private Store store;

    public Vendor(int userId,
        String firstName,
        String middleName,
        String lastName,
        String contactNumber,
        String email,
        Store store
    ){
        super(userId, firstName, middleName, lastName, contactNumber, email);
        this.store = store;
    }

    public Store getStore(){
        return store;
    }
}