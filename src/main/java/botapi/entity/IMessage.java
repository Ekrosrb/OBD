package botapi.entity;

import botapi.entity.utilEntity.IMessageEntity;

public class IMessage {
    private int message_id;
    private IUser from;
    private IChat chat;
    private int date;
    private String text;
    private IMessageEntity[] entities;

    public IMessage(int message_id, IUser from, IChat chat, int date, String text, IMessageEntity[] entities) {
        this.message_id = message_id;
        this.from = from;
        this.chat = chat;
        this.date = date;
        this.text = text;
        this.entities = entities;
    }

    public int getMessageId() {
        return message_id;
    }

    public IUser getUser() {
        return from;
    }

    public IChat getChat() {
        return chat;
    }

    public int getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public IMessageEntity[] getEntities() {
        return entities;
    }
}
