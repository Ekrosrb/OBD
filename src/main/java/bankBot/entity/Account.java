package bankBot.entity;

public class Account {
    private int id;
    private String balance;
    private String creditLimit;
    private String cashType;

    public Account(int id, String balance, String creditLimit, String cashType) {
        this.id = id;
        this.balance = balance;
        this.creditLimit = creditLimit;
        this.cashType = cashType;
    }

    public int getId() {
        return id;
    }

    public String getBalance() {
        return balance;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public String getCashType() {
        return cashType;
    }
}
