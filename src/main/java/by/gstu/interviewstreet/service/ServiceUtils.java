package by.gstu.interviewstreet.service;

import java.util.HashMap;
import java.util.Map;

public final class ServiceUtils {

    private ServiceUtils() { }

    public static Map<String, Object> buildValueMap(String[] keys, Object[] objects) throws IllegalArgumentException {
        if (keys.length != objects.length) {
            throw new IllegalArgumentException("Array length are not equal");
        }

        Map<String, Object> valueMap = new HashMap<>();
        for (int i = 0; i < keys.length ; i++) {
            valueMap.put(keys[i], objects[i]);
        }

        return valueMap;
    }

}
