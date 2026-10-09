package entities.user;

public abstract class User{    
    private int userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String contactNumber;
    private String email;

    User(int userId,
        String firstName,
        String middleName,
        String lastName,
        String contactNumber,
        String email
    ){
        this.userId = userId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public int getUserId(){
        return userId;
    }
    
    public String getFirstName(){
        return firstName;
    }

    public String getMiddleName(){
        return middleName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getContactNumber(){
        return contactNumber;
    }
    
    public String getEmail(){
        return email;
    }

}
