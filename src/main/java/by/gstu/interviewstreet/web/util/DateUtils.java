package by.gstu.interviewstreet.web.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils {

    public static final SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat YYYY = new SimpleDateFormat("yyyy");

    private DateUtils() { }

    public static Date getToday() {
        return Calendar.getInstance().getTime();
    }

    public static Date getTomorrow(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.get(Calendar.YEAR);
        return calendar.getTime();
    }

    public static boolean isToday(Date date) {
        String today = YYYY_MM_DD.format(getToday());
        String someday = YYYY_MM_DD.format(date);

        return today.equals(someday);
    }

    public static boolean isMoreThanToday(Date date) {
        return date.after(getToday());
    }

}
