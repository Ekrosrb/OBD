package bankBot.entity;

public class UserId {
    private String id;
    private String bank;

    public UserId(String id, String bank) {
        this.id = id;
        this.bank = bank;
    }

    public String getId() {
        return id;
    }

    public String getNameToken() {
        return bank;
    }
}
