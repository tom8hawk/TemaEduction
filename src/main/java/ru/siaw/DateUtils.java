package ru.siaw;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    private DateUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static int getDaysBetween(Calendar date1, Calendar date2) {
        long diffInMills = date2.getTime().getTime() - date1.getTime().getTime();
        return (int) TimeUnit.MILLISECONDS.toDays(diffInMills);
    }
}
