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
        String studentNumber,
        String program,
        int yearLevel,
        StudentWallet studentWallet
    ){
        super(userId, firstName, middleName, lastName, contactNumber, email);
        this.studentNumber = studentNumber;
        this.program = program;
        this.yearLevel = yearLevel;
        this.studentWallet = studentWallet;
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
}