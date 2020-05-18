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

    private List<Integer> users;
    private Host host;
    private TelegramBot t;
    private String[] menu = new String[]{"Добавить токен", "Курс", "Информация"};
    @Override
    public void onEventListener(IUpdate update) {

        String mess = update.getMessage().getText();
        IUser user = update.getMessage().getUser();
        switch (mess) {
            case "/start":

                t.sendMessage(update.getMessage().getChat(),
                        "/token - добавляет токен вашего банка.\n" +
                                "/rate - просмотр курса валют.\n" +
                                "/info - информация о счетах, депозитах и т.д.", menu);

                break;
            case "/token":
            case "Добавить токен":

                users.add(user.getId());
                t.sendMessage(update.getMessage().getChat(), "Введите ваш токен.", new String[]{"Назад"});

                break;
            case "/info":
            case "Информация":

                t.sendMessage(update.getMessage().getChat(), "Выберите банк.", new String[]{"Приват", "Моно", "Назад"});
                break;
            case "Моно":
            case "Приват":

                PersonData p = host.getInfo(new UserId(String.valueOf(user.getId()), mess));
                String info = mess + "банк\n\n";
                info += "Имя: " + p.getName() + "\n\n";
                if (p.getAccounts() != null) {
                    info += "Счета \n\n";
                    for (int i = 0; i < p.getAccounts().length; i++) {
                        Account a = p.getAccounts()[i];
                        info += " Имя счета: " + a.getId() + "\n" +
                                " Баланс: " + a.getBalance() + a.getCashType() + "\n" +
                                " Кредитный лимит: " + a.getCreditLimit() + a.getCashType() + "\n\n";
                    }
                } else {
                    info += "Счета не обнаружены.";
                }
                t.sendMessage(update.getMessage().getChat(), info, menu);

                break;
            case "Назад":
            case "/back":

                if (users.contains(user.getId())) {
                    for (int i = 0; i < users.size(); i++) {
                        if (users.get(i) == user.getId()) {
                            users.remove(i);
                        }
                    }
                }
                t.sendMessage(update.getMessage().getChat(), "Меню.", menu);

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
                        }
                    }
                    if(isAdd) {
                        t.sendMessage(update.getMessage().getChat(), "Токен банка был успешно добавлен.", menu);
                    }else{
                        t.sendMessage(update.getMessage().getChat(), "На данных момент сервер не отвечает, " +
                                "попробуйте позже.", menu);
                    }
                }

                break;
        }

    }

}
