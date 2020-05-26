package botapi.entity;
/**
 id	Integer	Unique identifier for this user or bot
is_bot	Boolean	True, if this user is a bot
first_name	String	User‘s or bot’s first name
last_name	String	Optional. User‘s or bot’s last name
username	String	Optional. User‘s or bot’s username
language_code	String	Optional. IETF language tag of the user's language
* */
public class IUser {
    private int id;
    private boolean is_bot;
    private String first_name;
    private String username;
    private String language_code;

    public IUser(int id, boolean is_bot, String first_name, String username, String language_code) {
        this.id = id;
        this.is_bot = is_bot;
        this.first_name = first_name;
        this.username = username;
        this.language_code = language_code;
    }

    public int getId() {
        return id;
    }

    public boolean isBot() {
        return is_bot;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getUserName() {
        return username;
    }

    public String getLanguageCode() {
        return language_code;
    }
}
