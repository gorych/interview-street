package by.gstu.interviewstreet.web.util;

import by.gstu.interviewstreet.domain.Interview;
import com.google.gson.*;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;

public class JSONParser {

    private static Gson GSON;

    static {
        final GsonBuilder builder = new GsonBuilder();
        builder
                .setDateFormat("yyyy-MM-dd")
                .excludeFieldsWithoutExposeAnnotation();

        GSON = builder.create();
    }

    private JSONParser() {
    }

    public static <T> T convertJsonStringToObject(String data, Class<T> clazz) {
        data = prepareRawJsonDataString(data);
        return GSON.fromJson(data, clazz);
    }

    public static <T> T convertJsonElementToObject(JsonElement jsonElement, Class<T> clazz) {
        String data = jsonElement.toString();
        return GSON.fromJson(data, clazz);
    }

    public static <T> T convertJsonElementToObject(JsonElement jsonElement, Type type) {
        String data = jsonElement.toString();
        return GSON.fromJson(data, type);
    }

    public static JsonArray convertJsonStringToJsonArray(String data) {
        data = prepareRawJsonDataString(data);
        return new JsonParser().parse(data).getAsJsonArray();
    }

    public static String convertObjectToJsonString(Object object) {
        return GSON.toJson(object);
    }

    public static String convertObjectToJsonString(Object object, Type type) {
        return GSON.toJson(object, type);
    }

    private static String prepareRawJsonDataString(String rawData) {
        try {
            rawData = URLDecoder.decode(rawData, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Data can't be converted to UTF-8", e);
        }

        int index = rawData.lastIndexOf("=");
        int lastSymbolIndex = rawData.length() - 1;

        return rawData.substring(0, index == lastSymbolIndex ? lastSymbolIndex : rawData.length());
    }

}
