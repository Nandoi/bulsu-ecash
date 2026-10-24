package entities.user;

public abstract class User{    
    private int userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String contactNumber;
    private String email;
    private String street;
    private String barangay;
    private String cityMunicipality;
    private String province;
    private String zipCode;
    

    protected User(int userId,
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
        this.userId = userId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.street = street;
        this.barangay = barangay;
        this.cityMunicipality = cityMunicipality;
        this.province = province;
        this.zipCode = zipCode;
    }

    public abstract String getUserType();

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

    public String getStreet() {
        return street;
    }

    public String getBarangay() {
        return barangay;
    }

    public String getCityMunicipality() {
        return cityMunicipality;
    }

    public String getProvince() {
        return province;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getFullAddress() {
        return street + ", " + barangay + ", " + cityMunicipality + ", " + province + " " + zipCode;
    }
}
