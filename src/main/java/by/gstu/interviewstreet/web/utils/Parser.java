package by.gstu.interviewstreet.web.utils;

public class Parser {

    public static Integer[] parseString(String data) {
        final String EMPTY = "";
        if (EMPTY.equals(data)) {
            return new Integer[0];
        }
        String[] strValues = data.split(",");
        Integer[] ids = new Integer[strValues.length];

        int errors = 0;
        for (int i = 0; i < ids.length; i++) {
            try {
                ids[i] = Integer.parseInt(strValues[i]);
            } catch (NumberFormatException e) {
                errors++;
            }
        }
        if (errors == strValues.length) {
            return new Integer[0];
        }
        return ids;
    }

}
