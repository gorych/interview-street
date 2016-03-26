package by.gstu.interviewstreet.web.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private DateUtils() {
    }

    public static Date getToday() {
        return Calendar.getInstance().getTime();
    }

    public static boolean isToday(Date date) {
        String today = sdf.format(getToday());
        String someday = sdf.format(date);

        return today.equals(someday);
    }

}
