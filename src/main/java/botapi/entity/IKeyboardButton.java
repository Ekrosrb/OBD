package botapi.entity;

public class IKeyboardButton {
    private String text;
    private boolean request_contact;
    private boolean request_location;
    private IKeyboardButtonPollType request_poll;

    public IKeyboardButton(String text, boolean request_contact, boolean request_location, IKeyboardButtonPollType request_poll) {
        this.text = text;
        this.request_contact = request_contact;
        this.request_location = request_location;
        this.request_poll = request_poll;
    }

    public String getText() {
        return text;
    }

    public boolean isRequest_contact() {
        return request_contact;
    }

    public boolean isRequest_location() {
        return request_location;
    }

    public IKeyboardButtonPollType getRequest_poll() {
        return request_poll;
    }
}
