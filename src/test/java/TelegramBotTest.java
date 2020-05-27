import botapi.entity.IMessage;
import botapi.entity.utilEntity.DataSendFormat;
import botapi.util.Json;
import com.google.gson.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TelegramBotTest extends Assert {


    private String token = "";  //Токен бота, иначе тесты не заработают.

    @Test
    public void getUpdateTest(){
        try {
            String url = "https://api.telegram.org/bot" + token + "/getMe";
            URL obj = new URL(url);
            HttpsURLConnection server = (HttpsURLConnection) obj.openConnection();
            server.setRequestMethod("GET");

            server.setRequestProperty("Content-Type", "application/json; utf-8");
            server.setRequestProperty("Accept", "application/json");
            server.setDoOutput(true);

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
    public void sendMessageTest(){
        try {
            DataSendFormat dataFormat = new DataSendFormat(426437267, "JUnitSendMessageTest", "", false, false, 0, null);
            String data = new Gson().toJson(dataFormat);
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
            System.out.println(sb.toString());
            assertTrue(sb.toString(), true);

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}

