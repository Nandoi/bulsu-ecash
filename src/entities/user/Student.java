package entities.user;

public class Student extends User{
    private String studentNumber;
    private String program;
    private int yearLevel;

    public Student(int userId,
        String firstName,
        String middleName,
        String lastName,
        String contactNumber,
        String email,
        String studentNumber,
        String program,
        int yearLevel
    ){
        super(userId, firstName, middleName, lastName, contactNumber, email);
        this.studentNumber = studentNumber;
        this.program = program;
        this.yearLevel = yearLevel;
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
}