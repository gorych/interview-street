package by.gstu.interviewstreet.web.util;

import by.gstu.interviewstreet.domain.PublishedInterview;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class PublishedInterviewSerializer implements JsonSerializer<PublishedInterview> {

    @Override
    public JsonElement serialize(PublishedInterview pInterview, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", pInterview.getId());
        jsonObject.addProperty("format-date", pInterview.getFormatDate());

        return jsonObject;
    }
}
