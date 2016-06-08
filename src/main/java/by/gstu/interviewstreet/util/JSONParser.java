package by.gstu.interviewstreet.util;

import by.gstu.interviewstreet.domain.PublishedInterview;
import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;

/**
 * Парсер объектов в JSON формат и обратно
 */
public class JSONParser {
    private static Logger LOG = LoggerFactory.getLogger(JSONParser.class);

    private static Gson GSON;

    /**
     * Статический блок инициализации GsonBuilder
     */
    static {
        final GsonBuilder builder = new GsonBuilder();
        builder
                .setDateFormat("yyyy-MM-dd")
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(PublishedInterview.class, new PublishedInterviewSerializer());

        GSON = builder.create();
    }

    private JSONParser() {
    }

    /**
     * Преобразует строку JSON к объекту заданного класса
     *
     * @param data  входня строка
     * @param clazz класс, к кторому преобразуем
     * @param <T>   тип класса
     * @return объект класса Т
     */
    public static <T> T convertJsonStringToObject(String data, Class<T> clazz) {
        data = prepareRawJsonDataString(data);
        return GSON.fromJson(data, clazz);
    }

    /**
     * Преобразует JSON элемент к объекту заданного класса
     *
     * @param jsonElement элемент для преобразования
     * @param clazz       класс, к кторому преобразуем
     * @param <T>         тип класса
     * @return объект класса Т
     */
    public static <T> T convertJsonElementToObject(JsonElement jsonElement, Class<T> clazz) {
        String data = jsonElement.toString();
        return GSON.fromJson(data, clazz);
    }

    /**
     * Преобразует JSON строку к объекту заданного типа
     *
     * @param data входная строка
     * @param type тип объекта
     * @return объект типа Т
     */
    public static <T> T convertJsonStringToObject(String data, Type type) {
        data = prepareRawJsonDataString(data);
        return GSON.fromJson(data, type);
    }

    /**
     * Преобразует JSON элемент к объекту заданного типа
     *
     * @param jsonElement элемент для преобразования
     * @param type        тип объекта
     * @return объект типа Т
     */
    public static <T> T convertJsonElementToObject(JsonElement jsonElement, Type type) {
        String data = jsonElement.toString();
        return GSON.fromJson(data, type);
    }

    /**
     * Преобразует JSON строку в JSON массив
     *
     * @param data входная строка
     * @return JSON массив
     */
    public static JsonArray convertJsonStringToJsonArray(String data) {
        data = prepareRawJsonDataString(data);
        return new JsonParser().parse(data).getAsJsonArray();
    }

    /**
     * Преобразует объект в JSON строку
     *
     * @param object объект для преобразования
     * @return JSON строка
     */
    public static String convertObjectToJsonString(Object object) {
        return GSON.toJson(object);
    }

    /**
     * Подготавливает данные для преобразования (удаляет некорректные символы)
     *
     * @param rawData исходные данные
     * @return корректная строка
     */
    private static String prepareRawJsonDataString(String rawData) {
        final String ERROR_MSG = "Data can't be converted to UTF-8";
        try {
            rawData = URLDecoder.decode(rawData, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error(ERROR_MSG, e);
            throw new IllegalArgumentException(ERROR_MSG, e);
        }

        int index = rawData.lastIndexOf("=");
        int lastSymbolIndex = rawData.length() - 1;

        return rawData.substring(0, index == lastSymbolIndex ? lastSymbolIndex : rawData.length());
    }

}
