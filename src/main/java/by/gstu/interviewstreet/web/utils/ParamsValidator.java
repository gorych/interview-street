package by.gstu.interviewstreet.web.utils;

import by.gstu.interviewstreet.web.utils.exceptions.FormParamException;

public class ParamsValidator {

    public static void checkAnswers(String[] answers) throws FormParamException {
        if (answers == null) {
            throw new FormParamException("Добавьте хотя бы один ответ на вопрос.");
        }

        for (String answer : answers) {
            checkString(answer, "ответа");
        }
    }

    public static void checkQuestion(String question) throws FormParamException {
        final String EMPTY = "";
        if (EMPTY.equals(question)) {
            throw new FormParamException("Текст вопроса не может быть пустым.");
        }

        final int MIN_LENGTH = 5;
        final int MAX_LENGTH = 100;
        if (question.length() < MIN_LENGTH || question.length() > MAX_LENGTH) {
            throw new FormParamException("Длина вопроса должна быть от 5 до 100 символов.");
        }
    }

    public static void checkString(String str, String fieldName) throws FormParamException {
        final String EMPTY = "";
        if (EMPTY.equals(str)) {
            throw new FormParamException("Поле " + fieldName + " не может быть пустым.");
        }
    }
}
