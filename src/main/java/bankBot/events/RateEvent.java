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

    private final TelegramBot t;
    private final Host host;
    private final String[] menu = new String[]{"Add token", "Exchange Rates", "Information"};
    private final String[] rateMenu = new String[]{"Monobank", "Privatbank", "UkrSibBank", "Back"};

    @Override
    public void onEventListener(IUpdate update) {
        String mess = update.getMessage().getText();
        if(mess.equals("/rate") || mess.equals("Exchange Rates")){
            t.sendMessage(update.getMessage().getChat(), "Enter the name of the bank.", rateMenu);
        }else{
            switch (mess) {
                case "Monobank": {

                    MonobankRate[] r = host.getMonobankRates();
                    if(r != null) {
                        String data = "Monobank exchange rate \n\n";
                        for (MonobankRate monobankRate : r) {
                            if (monobankRate.getRateBuy() == 0.0 && monobankRate.getRateSell() == 0.0) {
                                continue;
                            }
                            Currency a = getCurrencyInstance(monobankRate.getCurrencyCodeA());
                            Currency b = getCurrencyInstance(monobankRate.getCurrencyCodeB());
                            data += a.getCurrencyCode() + "\n";
                            data += "Buy: " + monobankRate.getRateBuy() + b.getSymbol() + "\n";
                            data += "Sale: " + monobankRate.getRateSell() + b.getSymbol();
                            data += "\n\n";
                        }
                        t.sendMessage(update.getMessage().getChat(), data, rateMenu);
                    }
                    break;
                }
                case "Privatbank": {
                    PrivatbankRate[] r = host.getPrivatbankRates();
                    if(r != null) {
                        String data = "PrivatBank exchange rate\n\n";
                        for (PrivatbankRate privatbankRate : r) {
                            Currency a = Currency.getInstance(privatbankRate.getBaseCcy());
                            data += privatbankRate.getCcy() + "\n";
                            data += "Buy: " + privatbankRate.getRateBuy() + a.getSymbol() + "\n";
                            data += "Sale: " + privatbankRate.getRateSell() + a.getSymbol();
                            data += "\n\n";
                        }
                        t.sendMessage(update.getMessage().getChat(), data, rateMenu);
                    }
                    break;
                }
                case "UkrSibBank":
                    if(host.getUrsibbankRate() != null) {
                        t.sendMessage(update.getMessage().getChat(), host.getUrsibbankRate(), rateMenu);
                    }
                    break;
                case "Back":
                case "/back":
                    t.sendMessage(update.getMessage().getChat(), "Menu.", menu);
                    break;
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
