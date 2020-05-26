package botapi.util;

import botapi.entity.*;
import botapi.entity.utilEntity.DataSendFormat;
import botapi.entity.utilEntity.IUpdate;
import botapi.listeners.EventListener;
import com.google.gson.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * TelegramBot - the class provides communication with your telegram bot.
 * Only a small part of what is in api is implemented here.
 * @author ekros
 * @version 1.0
 *
 * */
public class TelegramBot {
    public TelegramBot(String token){
        this.token = token;
        events = new ArrayList<>();
    }

    /**
     * Establishing a connection with the bot.
     * Running a stream to receive and process data.
     * */
    public void connect(){
        connect = true;
        IUpdate[] u = URLBotConnection.update(token, id);
        for (IUpdate update : u != null ? u : new IUpdate[0]) {
            if (update.getUpdate_id() > id) {
                id = update.getUpdate_id();
            }
        }
        id++;
        listener.start();
        System.out.println("Connected with token.");
    }
    /**
     * Closing the connection, and stop receiving updates.
     * */
    public void close(){
        connect = false;
        System.out.println("Connection close.");
    }

    private boolean connect;
    private String token;
    private List<EventListener> events;
    private int id = 0;
    private Thread listener = new Thread(() -> {
        Date date = new Date();
        for(;connect;){
            if(date.getTime() + 2500 < new Date().getTime()){
                IUpdate[] u = URLBotConnection.update(token, id);
                if((u != null ? u.length : 0) != 0) {
                    for (EventListener event : events) {
                        for (IUpdate update : u) {
                            event.onEventListener(update);
                        }
                    }
                    for (IUpdate update : u) {
                        if (update.getUpdate_id() > id) {
                            id = update.getUpdate_id();
                        }
                    }
                    id++;
                }
                date = new Date();
            }
        }
    });

    public void addEventListener(EventListener eventListener){
        if(!events.contains(eventListener)){
            events.add(eventListener);
        }
    }

    public void removeEventListener(EventListener eventListener){
        events.remove(eventListener);
    }

    /**
     * Send a text message
     * @param chat - chat where the message will be sent (available from IUpdate).
     * @param text - message text
     * */
    public synchronized void sendMessage(IChat chat, String text){
        DataSendFormat data = new DataSendFormat(chat.getId(), text, "", false, false, 0, null);
        URLBotConnection.sendMessage(token, new Gson().toJson(data));
    }

    /**
     * Send a text message with buttons.
     * @param chat - chat where the message will be sent (available from IUpdate).
     * @param text - message text
     * @param buttons - buttons name (new String[]{"button1","button2","button3"})
     * */
    public synchronized void sendMessage(IChat chat, String text, String[] buttons){
        IKeyboardButton[][] but = new IKeyboardButton[buttons.length][1];
        for(int i = 0; i < buttons.length; i++){
            but[i][0] = new IKeyboardButton(buttons[i], false, false, null);
        }
        IReplyKeyboardMarkup keyboard = new IReplyKeyboardMarkup(but, true, true, false);
        DataSendFormat data = new DataSendFormat(chat.getId(), text, "", false, false, 0, keyboard);
        URLBotConnection.sendMessage(token, new Gson().toJson(data));
    }
    /**
     * The class implements methods for boots via http
     * requests using standard java tools, and the gson
     * library for working with json.
     * @author ekros
     * @version 1.0
     *
     * */
    private static class URLBotConnection {
        public static IUpdate[] update(String token, int id){
            try {
                String url = "https://api.telegram.org/bot" + token + "/getUpdates";
                URL obj = new URL(url);
                HttpsURLConnection server = (HttpsURLConnection) obj.openConnection();
                server.setRequestMethod("GET");

                server.setRequestProperty("Content-Type", "application/json; utf-8");
                server.setRequestProperty("Accept", "application/json");
                server.setDoOutput(true);
                JsonObject o = new JsonObject();
                JsonElement jsonElement = new JsonPrimitive(id);
                o.add("offset", jsonElement);
                try(OutputStream out = server.getOutputStream()) {
                    byte[] input = o.toString().getBytes(StandardCharsets.UTF_8);
                    out.write(input, 0, input.length);
                }

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(server.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line;
                for(;(line = in.readLine()) != null;){
                    sb.append(line);
                }

                JsonObject jsonObject = new JsonParser().parse(sb.toString()).getAsJsonObject();

                return Json.update(jsonObject.get("result").toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static IMessage sendMessage(String token, String data){
            try {
                String url = "https://api.telegram.org/bot" + token + "/sendMessage";
                URL obj = new URL(url);
                HttpsURLConnection server = (HttpsURLConnection) obj.openConnection();
                server.setRequestMethod("POST");
                server.setRequestProperty("Content-Type", "application/json; utf-8");
                server.setRequestProperty("Accept", "application/json");
                server.setDoOutput(true);

                try(OutputStream out = server.getOutputStream()) {
                    byte[] input = data.getBytes(StandardCharsets.UTF_8);
                    out.write(input, 0, input.length);
                }

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(server.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line;
                for(;(line = in.readLine()) != null;){
                    sb.append(line);
                }

                JsonObject jsonObject = new JsonParser().parse(sb.toString()).getAsJsonObject();
                return Json.message(jsonObject.get("result").toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
