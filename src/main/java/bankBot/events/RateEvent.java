package bankBot.events;

import bankBot.Host;
import bankBot.entity.MonobankRate;
import bankBot.entity.PrivatbankRate;
import botapi.entity.utilEntity.IUpdate;
import botapi.listeners.EventListener;
import botapi.util.TelegramBot;

import java.util.Currency;
import java.util.Set;

public class RateEvent implements EventListener {
    public RateEvent(TelegramBot t, Host host){
        this.host = host;
        this.t = t;
    }

    private TelegramBot t;
    private Host host;
    private String[] menu = new String[]{"Добавить токен", "Курс", "Информация"};
    private String[] rateMenu = new String[]{"Монобанк", "Приватбанк", "УкрСибБанк", "Назад"};

    @Override
    public void onEventListener(IUpdate update) {
        String mess = update.getMessage().getText();
        if(mess.equals("/rate") || mess.equals("Курс")){
            t.sendMessage(update.getMessage().getChat(), "Введите название банка.", rateMenu);
        }else{
            if(mess.equals("Монобанк")) {

                MonobankRate[] r = host.getMonobankRates();
                String data = "Курс Monobank \n\n";
                for (int i = 0; i < r.length; i++) {
                    if (r[i].getRateBuy() == 0.0 && r[i].getRateSell() == 0.0) {
                        continue;
                    }
                    Currency a = getCurrencyInstance(r[i].getCurrencyCodeA());
                    Currency b = getCurrencyInstance(r[i].getCurrencyCodeB());
                    data += a.getCurrencyCode() + "\n";
                    data += "Покупка: " + r[i].getRateBuy() + b.getSymbol() + "\n";
                    data += "Продажа: " + r[i].getRateSell() + b.getSymbol();
                    data += "\n\n";
                }
                t.sendMessage(update.getMessage().getChat(), data, rateMenu);
            }else if(mess.equals("Приватбанк")){
                PrivatbankRate[] r = host.getPrivatbankRates();
                String data = "Курс PrivatBank \n\n";
                for (int i = 0; i < r.length; i++) {
                    Currency a = Currency.getInstance(r[i].getBaseCcy());
                    data += r[i].getCcy() + "\n";
                    data += "Покупка: " + r[i].getRateBuy() + a.getSymbol() + "\n";
                    data += "Продажа: " + r[i].getRateSell() + a.getSymbol();
                    data += "\n\n";
                }
                t.sendMessage(update.getMessage().getChat(), data, rateMenu);
            }else if(mess.equals("УкрСибБанк")){
                Thread getData = new Thread(()->{
                    IUpdate u = update;
                    t.sendMessage(u.getMessage().getChat(), host.getUrsibbankRate(), rateMenu);
                });
                getData.start();
            }else if(mess.equals("Назад") || mess.equals("/back")){
                t.sendMessage(update.getMessage().getChat(), "Меню.", menu);
            }
        }
    }

    private Currency getCurrencyInstance(int numericCode) {
        Set<Currency> currencies = Currency.getAvailableCurrencies();
        for (Currency currency : currencies) {
            if (currency.getNumericCode() == numericCode) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Currency with numeric code "  + numericCode + " not found");
    }
}
