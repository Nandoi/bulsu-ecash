package entities.user;

import  entities.wallet.StudentWallet;
public class Student extends User{
    private String studentNumber;
    private String program;
    private int yearLevel;    
    private StudentWallet studentWallet;

    public Student(int userId,
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
        String studentNumber,
        String program,
        int yearLevel
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
        this.studentNumber = studentNumber;
        this.program = program;
        this.yearLevel = yearLevel;
    }
    
    @Override
    public String getUserType() {
        return "STUDENT";
    }

    public String getStudentNumber(){
        return studentNumber;
    }

    public String getProgram(){
        return program;
    }

    public int getYearLevel(){
        return yearLevel;
    }

    public StudentWallet getStudentWallet() {
        return studentWallet;
    }

    public boolean assignStudentWallet(StudentWallet studentWallet) {
        if (this.studentWallet != null || studentWallet == null) {
            return false;
        }

        this.studentWallet = studentWallet;
        return true;
    }
}