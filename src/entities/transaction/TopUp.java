package entities.transaction;

import entities.user.Cashier;
import entities.user.Student;
import entities.wallet.StudentWallet;
public class TopUp extends TransactionRecord {
    private int topUpId;

    //Related objects na gagamitin
    private Cashier cashier;
    private Student student;
    private StudentWallet studentWallet;

    public TopUp(int topUpId, double amount, String dateTime, Cashier cashier, Student student, StudentWallet studentWallet){
        super(amount, dateTime);
        this.topUpId = topUpId;
        this.cashier = cashier;
        this.student = student;
        this.studentWallet = studentWallet;
    }

    @Override
    public String getTransactionType() {
        return "TOP_UP";
    }

    public int getTopUpId() {
        return topUpId;
    }
    public Cashier getCashier() {
        return cashier;
    }

    public Student getStudent(){
        return student;
    }

    public StudentWallet getStudentWallet() {
        return studentWallet;
    }
}