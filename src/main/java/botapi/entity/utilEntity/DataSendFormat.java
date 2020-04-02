package botapi.entity.utilEntity;

import botapi.entity.IReplyKeyboardMarkup;

public class DataSendFormat {
    private int chat_id;
    private String text;
    private String parse_mode;
    private boolean disable_web_page_preview;
    private boolean disable_notification;
    private int reply_to_message_id;
    private IReplyKeyboardMarkup reply_markup;

    public DataSendFormat(int chat_id, String text, String parse_mode, boolean disable_web_page_preview, boolean disable_notification, int reply_to_message_id, IReplyKeyboardMarkup reply_markup) {
        this.chat_id = chat_id;
        this.text = text;
        this.parse_mode = parse_mode;
        this.disable_web_page_preview = disable_web_page_preview;
        this.disable_notification = disable_notification;
        this.reply_to_message_id = reply_to_message_id;
        this.reply_markup = reply_markup;
    }

    public int getChat_id() {
        return chat_id;
    }

    public String getText() {
        return text;
    }

    public String getParse_mode() {
        return parse_mode;
    }

    public boolean isDisable_web_page_preview() {
        return disable_web_page_preview;
    }

    public boolean isDisable_notification() {
        return disable_notification;
    }

    public int getReply_to_message_id() {
        return reply_to_message_id;
    }

    public IReplyKeyboardMarkup getReply_markup() {
        return reply_markup;
    }
}
