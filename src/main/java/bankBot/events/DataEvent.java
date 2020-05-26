package bankBot.events;

import bankBot.Host;
import bankBot.entity.PersonData;
import bankBot.entity.Account;
import bankBot.entity.UserId;
import botapi.entity.IUser;
import botapi.entity.utilEntity.IUpdate;
import botapi.listeners.EventListener;
import botapi.util.TelegramBot;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.*;

public class DataEvent implements EventListener {
    public DataEvent(TelegramBot t, Host host){
        this.t = t;
        this.host = host;
        users = new ArrayList<>();
    }

    private final List<Integer> users;
    private final Host host;
    private final TelegramBot t;
    private final String[] menu = new String[]{"Add token", "Exchange Rates", "Information"};
    @Override
    public void onEventListener(IUpdate update) {

        String mess = update.getMessage().getText();
        IUser user = update.getMessage().getUser();
        switch (mess) {
            case "/start":

                t.sendMessage(update.getMessage().getChat(),
                        "/token - Adds your bank token.\n" +
                                "/rate - view exchange rates.\n" +
                                "/info - information about accounts, deposits, etc. ", menu);

                break;
            case "/token":
            case "Add token":
                if(users.contains(user.getId())){
                    break;
                }
                users.add(user.getId());
                t.sendMessage(update.getMessage().getChat(), "Enter your token.", new String[]{"Back"});

                break;
            case "/info":
            case "Information":
                t.sendMessage(update.getMessage().getChat(), "Choose a bank.", new String[]{"Private", "Mono", "Back"});
                break;
            case "Mono":
            case "Private":

                PersonData p = host.getInfo(new UserId(String.valueOf(user.getId()), mess));
                if(p == null){
                    t.sendMessage(update.getMessage().getChat(),
                            "At the moment the server does not respond, try again later.", menu);
                }else {
                    String info = mess + "банк\n\n";
                    info += "Name:" + p.getName() + "\n\n";
                    if (p.getAccounts() != null) {
                        info += "Accounts \n\n";
                        for (int i = 0; i < p.getAccounts().length; i++) {
                            Account a = p.getAccounts()[i];
                            info += " Account Name: " + a.getId() + "\n" +
                                    " Balance: " + a.getBalance() + a.getCashType() + "\n" +
                                    " Credit limit: " + a.getCreditLimit() + a.getCashType() + "\n\n";
                        }
                    } else {
                        info += "No accounts were found.";
                    }
                    t.sendMessage(update.getMessage().getChat(), info, menu);
                }

                break;
            case "Back":
            case "/back":

                if (users.contains(user.getId())) {
                    for (int i = 0; i < users.size(); i++) {
                        if (users.get(i) == user.getId()) {
                            users.remove(i);
                            t.sendMessage(update.getMessage().getChat(), "Menu.", menu);
                            break;
                        }
                    }
                }

                break;
            default:

                if (users.contains(user.getId())) {
                    JsonObject data = new JsonObject();
                    data.add("id", new JsonPrimitive(user.getId()));
                    data.add("token", new JsonPrimitive(update.getMessage().getText()));
                    boolean isAdd = host.sendData(data.toString());
                    for (int i = 0; i < users.size(); i++) {
                        if (users.get(i) == user.getId()) {
                            users.remove(i);
                            if(isAdd) {
                                t.sendMessage(update.getMessage().getChat(),
                                        "Bank token has been successfully added.", menu);
                            }else{
                                t.sendMessage(update.getMessage().getChat(),
                                        "At the moment the server does not respond, try again later.", menu);
                            }
                            break;
                        }
                    }
                }

                break;
        }

    }

}
