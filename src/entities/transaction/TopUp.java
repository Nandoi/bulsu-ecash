package entities.transaction;

import entities.user.Cashier;
import entities.wallet.StudentWallet;
public class TopUp extends TransactionRecord{
    private int topUpId;

    //Related objects na gagamitin
    private Cashier cashier;
    private StudentWallet studentWallet;

    public TopUp(int topUpId,
        double amount,
        String dateTime,
        Cashier cashier,
        StudentWallet studentWallet
    ){
        super(amount, dateTime);
        this.topUpId = topUpId;
        this.cashier = cashier;
        this.studentWallet = studentWallet;
    }

    public int getTopUpId() {
        return topUpId;
    }
    public Cashier getCashier() {
        return cashier;
    }

    public StudentWallet getStudentWallet() {
        return studentWallet;
    }
}