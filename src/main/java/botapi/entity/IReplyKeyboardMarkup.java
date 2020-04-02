package botapi.entity;

public class IReplyKeyboardMarkup {
    private IKeyboardButton[][] keyboard;
    private boolean resize_keyboard;
    private boolean one_time_keyboard;
    private boolean selective;

    public IReplyKeyboardMarkup(IKeyboardButton[][] keyboard, boolean resize_keyboard, boolean one_time_keyboard, boolean selective) {
        this.keyboard = keyboard;
        this.resize_keyboard = resize_keyboard;
        this.one_time_keyboard = one_time_keyboard;
        this.selective = selective;
    }

    public IKeyboardButton[][] getKeyboard() {
        return keyboard;
    }

    public boolean isResize_keyboard() {
        return resize_keyboard;
    }

    public boolean isOne_time_keyboard() {
        return one_time_keyboard;
    }

    public boolean isSelective() {
        return selective;
    }
}
