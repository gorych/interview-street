package by.gstu.interviewstreet.web.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Locale;

@Component
public class MessageUtils {

    @Autowired
    MessageSource resource;

    public String getMessageFromBindingResult(BindingResult bindingResult) {
        StringBuilder builder = new StringBuilder();
        for (FieldError error : bindingResult.getFieldErrors()) {
            builder.append(resource.getMessage(error, Locale.getDefault()))
                    .append("\n");
        }
        return builder.toString();
    }
}
