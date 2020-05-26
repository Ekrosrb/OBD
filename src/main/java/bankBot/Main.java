package bankBot;

import bankBot.events.DataEvent;
import bankBot.events.RateEvent;
import botapi.util.TelegramBot;


public class Main {
    public static void main(String[] args) {
        String token = ""; //There should be a token.
        TelegramBot t = new TelegramBot(token);
        Host host = new Host(""); //server url which communicates with banks api.
        t.addEventListener(new DataEvent(t, host));
        t.addEventListener(new RateEvent(t, host));
        t.connect();
    }
}
