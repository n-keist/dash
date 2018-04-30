package io.lemony.dash;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.lemony.dash.ws.WSCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Utils {

    private static Gson gson = new GsonBuilder().create();
    public static Map<String, WSCommand> ws_commandmap = new HashMap<>();

    public static String randomString(int length) {
        String[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".split("");
        String finalRandomString = "";
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            finalRandomString += chars[random.nextInt(chars.length)];
        }
        return finalRandomString;
    }

    public static Gson getGson() {
        return gson;
    }
}
