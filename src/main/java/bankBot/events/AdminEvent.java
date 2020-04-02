package bankBot.events;

import bankBot.Host;
import botapi.entity.utilEntity.IUpdate;
import botapi.listeners.EventListener;
import botapi.util.TelegramBot;

public class AdminEvent implements EventListener {
    public AdminEvent(TelegramBot t, Host host) {
        this.t = t;
        this.host = host;
    }

    private TelegramBot t;
    private Host host;
    @Override
    public void onEventListener(IUpdate update) {
        if(update.getMessage().getUser().getId() == 426437267){
            String comm = update.getMessage().getText();
            if(comm.equals("/close")){
                t.sendMessage(update.getMessage().getChat(), "Бот выключен.");
                t.close();
                host.close();

            }
        }
    }
}
