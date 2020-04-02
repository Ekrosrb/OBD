package bankBot;

import bankBot.events.AdminEvent;
import bankBot.events.MyEvent;
import bankBot.events.RateEvent;
import botapi.util.TelegramBot;


public class Main {
    public static void main(String[] args) {
        String token = ""; //Здесь должен быть токен.
        TelegramBot t = new TelegramBot(token);
        Host host = new Host(""); //url сервера который связывается с api банков.
        t.addEventListener(new MyEvent(t, host));
        t.addEventListener(new RateEvent(t, host));
        t.addEventListener(new AdminEvent(t, host));
        t.connect();


    }
}
