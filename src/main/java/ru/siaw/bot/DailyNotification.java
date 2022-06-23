package ru.siaw.bot;

import lombok.Getter;
import ru.siaw.DateUtils;
import ru.siaw.database.Course;
import ru.siaw.database.Database;
import ru.siaw.database.Poll;
import ru.siaw.database.User;
import ru.siaw.messages.MessageHandler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static ru.siaw.Main.executor;

/**  Начинает опрос пользователей о их дне обучения */
public class DailyNotification {
    // Запланированная задача
    @Getter private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private DailyNotification() {
        throw new IllegalStateException("Utility class");
    }

    public static void start() { // Запускает задачу каждый день в 19:00
        scheduler.schedule(DailyNotification::run, calculateNext(), TimeUnit.SECONDS);
    }

    private static void run() {
        Calendar today = new GregorianCalendar();

        for (User user : new ArrayList<>(Database.getUsers())) {
            executor.execute(() -> {
                if (user.getCourse() != null) {
                    Course course = user.getCourse();

                    if (course.getStartTime() != null && course.getEndTime() != null) {
                        Calendar startTime = course.getStartTime();

                        if (!isToday(startTime, today)) {
                            Calendar endTime = course.getEndTime();
                            Bot.removeFromWaiting(user.getId());

                            if (endTime.after(today)) {
                                MessageHandler.dayIsOver(user.getId());

                                course.getPolls().add(new Poll(DateUtils.getDaysBetween(startTime, today)));
                                Bot.getAtThePoll().put(user.getId(), course);
                            } else {
                                Database.getUsers().remove(user);
                                MessageHandler.endOfCourse(user.getId());
                            }
                        }
                    }
                }
            });
        }

        start();
    }

    private static boolean isToday(Calendar calendar, Calendar today) {
        return calendar.get(Calendar.ERA) == today.get(Calendar.ERA) &&
               calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
               calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR);
    }

    private static long calculateNext() { // Остаток времени до 19:00 или следующий день, если времени уже больше
        LocalDateTime localNow = LocalDateTime.now();
        ZoneId currentZone = ZoneId.systemDefault();

        ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
        ZonedDateTime zonedTarget = zonedNow.withHour(19).withMinute(0).withSecond(0);

        if (zonedNow.compareTo(zonedTarget) > -1)
            zonedTarget = zonedTarget.plusDays(1);

        return Duration.between(zonedNow, zonedTarget).getSeconds();
    }
}
