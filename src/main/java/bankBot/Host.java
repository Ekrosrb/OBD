package bankBot;

import bankBot.entity.MonobankRate;
import bankBot.entity.PersonData;
import bankBot.entity.PrivatbankRate;
import bankBot.entity.UserId;
import com.google.gson.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Host - the class has methods for obtaining
 * information from the server, as well as for
 * obtaining the exchange rate.
 * @author ekros
 * @version 1.0
 * */


public class Host {
    /**
     * In the constructor, all the necessary information
     * is obtained, a stream is also created to update
     * this information once at a given time.
     *
     * */
    public Host(String url){
        this.url = url;
        updateMs = 3600000;
        isUpdate = true;
        try {
            monobankRates = monoRate();
            privatbankRates = privatRate();
            ursibbankRate = uksibBank();
            System.out.println("Данные о курсах валют загружены. " + new Date().toString());
        }catch (Exception e){
            System.out.println("При получении данных произошла ошибка:");
            System.out.println(e.getMessage());
        }
        update = new Thread(()->{
            Date last = new Date();
            for(;isUpdate;){
                if(last.getTime()+updateMs < new Date().getTime()){
                    try {
                        monobankRates = monoRate();
                        privatbankRates = privatRate();
                        ursibbankRate = uksibBank();
                        System.out.println("Данные о курсах валют загружены. " + new Date().toString());
                    }catch (Exception e){
                        System.out.println("При получении данных произошла ошибка:");
                        System.out.println(e.getMessage());
                    }
                    last = new Date();
                }
            }
        });
        update.setDaemon(true);
        update.start();

    }
    private boolean isUpdate;
    private int updateMs; //Information refresh period(ms)
    private final String url; //Server url

    private MonobankRate[] monobankRates;
    private PrivatbankRate[] privatbankRates;
    private String ursibbankRate;

    private final Thread update;
    /***/
    public boolean sendData(String data){

        try {
            URL obj = new URL(url + "/test");
            HttpURLConnection server = (HttpURLConnection) obj.openConnection();
            server.setRequestMethod("GET");
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
            return true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    /**
     * Getting personal information from different
     * banks about the client.
     * @see PersonData
     * @return PersonData
     * */
    public PersonData getInfo(UserId id){

        try {
            URL obj = new URL(url + "/balance");
            HttpURLConnection server = (HttpURLConnection) obj.openConnection();
            server.setRequestMethod("GET");
            server.setRequestProperty("Content-Type", "application/json; utf-8");
            server.setRequestProperty("Accept", "application/json");
            server.setDoOutput(true);

            try(OutputStream out = server.getOutputStream()) {
                byte[] input = new Gson().toJson(id).toString().getBytes(StandardCharsets.UTF_8);
                out.write(input, 0, input.length);
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(server.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;
            for(;(line = in.readLine()) != null;){
                sb.append(line);
            }
            return new Gson().fromJson(sb.toString(), PersonData.class);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    private MonobankRate[] monoRate(){
        try {
            String url = "https://api.monobank.ua/bank/currency";
            URL obj = new URL(url);
            HttpsURLConnection server = (HttpsURLConnection) obj.openConnection();
            server.setRequestMethod("GET");
            server.setRequestProperty("Content-Type", "application/json; utf-8");
            server.setRequestProperty("Accept", "application/json");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(server.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;
            for(;(line = in.readLine()) != null;){
                sb.append(line);
            }

            return new Gson().fromJson(sb.toString(), MonobankRate[].class);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    private PrivatbankRate[] privatRate(){
        try {
            String url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
            URL obj = new URL(url);
            HttpsURLConnection server = (HttpsURLConnection) obj.openConnection();
            server.setRequestMethod("GET");
            server.setRequestProperty("Content-Type", "application/json; utf-8");
            server.setRequestProperty("Accept", "application/json");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(server.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            for(;(line = in.readLine()) != null;){
                sb.append(line);
            }

            return new Gson().fromJson(sb.toString(), PrivatbankRate[].class);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    private String uksibBank(){
        try {
            Document doc  = Jsoup.connect("https://my.ukrsibbank.com/ru/personal/operations/currency_exchange/").get();
            Elements els = doc.select("table.currency__table").select("tbody").select("tr");
            StringBuilder data = new StringBuilder("UKRSIBBANK\n\n");
            for(int i = 0; i < els.size(); i++){
                Elements e = els.get(i).select("td");
                String s = e.get(0).ownText();
                data.append(s, 0, s.length() - 1).append("\n");
                data.append("Покупка: ").append(e.get(1).ownText()).append("₴\n");
                data.append("Продажа: ").append(e.get(2).ownText()).append("₴\n\n");
            }
            return data.toString();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    /**
     * Stop receiving updates.
     *
     * */
    public void close(){
        isUpdate = false;
    }

    public MonobankRate[] getMonobankRates() {
        return monobankRates;
    }

    public PrivatbankRate[] getPrivatbankRates() {
        return privatbankRates;
    }

    public String getUrsibbankRate() {
        return ursibbankRate;
    }

    public void setUpdateMs(int updateMs) {
        this.updateMs = updateMs;
    }

}
