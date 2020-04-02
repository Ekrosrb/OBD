package botapi.entity.utilEntity;

import botapi.entity.IMessage;

public class IUpdate {
    private int update_id;
    private IMessage message;
    private IMessage edited_message;
    private IMessage channel_post;
    private IMessage edited_channel_post;


    public IUpdate(int update_id, IMessage message) {
        this.update_id = update_id;
        this.message = message;
    }

    public int getUpdate_id() {
        return update_id;
    }

    public IMessage getMessage() {
        return message;
    }
}
