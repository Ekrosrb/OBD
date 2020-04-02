package botapi.entity;

public class IChat {
    private int id;
    private String first_name;
    private String username;
    private String type;

    public IChat(int id, String first_name, String username, String type) {
        this.id = id;
        this.first_name = first_name;
        this.username = username;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }
}
