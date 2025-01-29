package model;

import main.Payable;

public class Client extends Person implements Payable {

    private int memberId;
    private Amount balance;

    private final int MEMBER_ID = 456;
    private final Amount BALANCE = new Amount(50);

    public Client(int memberId, Amount balance, String name) {
        super(name);
        this.memberId = memberId;
        this.balance = balance;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public Amount getBalance() {
        return balance;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }

    public int getMEMBER_ID() {
        return MEMBER_ID;
    }

    public Amount getBALANCE() {
        return BALANCE;
    }

    public String getName() {
        return name;
    }
    

    @Override
    public boolean pay(Amount amount) {
        Amount diferencia = this.balance.subtract(amount);
        if (diferencia.getValue() >= 0) {
            this.balance = this.balance.subtract(amount);
            return true;
        }
        this.balance = this.balance.subtract(amount);
        return false;
    }

}
