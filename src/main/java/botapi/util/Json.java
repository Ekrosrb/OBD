package botapi.util;

import botapi.entity.IMessage;
import botapi.entity.utilEntity.IUpdate;
import com.google.gson.Gson;

public class Json {

    public static IUpdate[] update(String json){
        return new Gson().fromJson(json, IUpdate[].class);
    }
    public static IMessage message(String json){
        return new Gson().fromJson(json, IMessage.class);
    }
}
