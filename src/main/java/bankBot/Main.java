package bankBot;

import bankBot.events.DataEvent;
import bankBot.events.RateEvent;
import botapi.util.TelegramBot;


public class Main {
    public static void main(String[] args) {
        String token = "1095042140:AAHOvhoYy8ntMZjaui6KrdOHLg9Ybdllb9Q"; //Здесь должен быть токен.
        TelegramBot t = new TelegramBot(token);
        Host host = new Host(""); //url сервера который связывается с api банков.
        t.addEventListener(new DataEvent(t, host));
        t.addEventListener(new RateEvent(t, host));
        t.connect();
    }
}
