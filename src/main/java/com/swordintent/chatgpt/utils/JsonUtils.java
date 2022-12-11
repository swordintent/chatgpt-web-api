package com.swordintent.chatgpt.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author liuhe
 */
public class JsonUtils {
    private static final Gson prettyGson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static final Gson gson = new GsonBuilder()
            .create();

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> t) {
        return gson.fromJson(json, t);
    }
}
