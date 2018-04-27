package io.lemony.dash;

import java.util.Random;

public class Utils {

    public static String randomString(int length) {
        String[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".split("");
        String finalRandomString = "";
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            finalRandomString += chars[random.nextInt(chars.length)];
        }
        return finalRandomString;
    }

}
