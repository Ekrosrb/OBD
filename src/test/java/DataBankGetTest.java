
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class DataBankGetTest extends Assert {
    @Test
    public void monoBankRateTest(){
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
            System.out.println(sb.toString());
            assertTrue(sb.toString(),true);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void privatRateTest(){
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
            System.out.println(sb.toString());
            assertTrue(sb.toString(), true);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void uksibBankTest(){
        try {
            Document doc  = Jsoup.connect("https://my.ukrsibbank.com/ru/personal/operations/currency_exchange/").get();
            Elements els = doc.select("table.currency__table").select("tbody").select("tr");
            StringBuilder data = new StringBuilder("UKRSIBBANK\n\n");
            for(int i = 0; i < els.size(); i++){
                Elements e = els.get(i).select("td");
                String s = e.get(0).ownText();
                data.append(s, 0, s.length() - 1).append("\n");
                data.append("Buy: ").append(e.get(1).ownText()).append("₴\n");
                data.append("Sale: ").append(e.get(2).ownText()).append("₴\n\n");
            }
            System.out.println(data.toString());
            assertTrue(data.toString(), true);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
