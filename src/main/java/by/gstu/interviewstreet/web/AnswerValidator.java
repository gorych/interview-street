package by.gstu.interviewstreet.web;

import by.gstu.interviewstreet.domain.Answer;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AnswerValidator implements Validator {

    private static final int MIN_RATING_VALUE = 3;
    private static final int MAX_RATING_VALUE = 10;

    private static final String RATING_ANSWER = "rating";
    private static final String VALIDATE_FIELD = "text";

    @Override
    public boolean supports(Class<?> clazz) {
        return Answer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Answer answer = (Answer) obj;

        String value = answer.getText();
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, VALIDATE_FIELD, "Value is empty");

        if (RATING_ANSWER.equals(answer.getType().getName())) {
            try {
                int number = Integer.parseInt(value);
                if (number < MIN_RATING_VALUE || number > MAX_RATING_VALUE) {
                    errors.rejectValue(VALIDATE_FIELD, "Value is less than 3 or less than 10");
                }
            } catch (NumberFormatException e) {
                errors.rejectValue(VALIDATE_FIELD, "Value cannot be converted to Integer");
            }
        }
    }
}
