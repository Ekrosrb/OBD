package bankBot.entity;

import bankBot.entity.Account;

public class PersonData {
    private String name;
    private Account[] accounts;

    public PersonData(String name, Account[] accounts) {
        this.name = name;
        this.accounts = accounts;
    }

    public String getName() {
        return name;
    }

    public Account[] getAccounts() {
        return accounts;
    }
}
