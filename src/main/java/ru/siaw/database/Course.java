package ru.siaw.database;

import lombok.Getter;
import lombok.Setter;
import ru.siaw.DateUtils;
import ru.siaw.motivation.Goal;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

/** Объект, описывающий курс, который проходит студент */
public class Course {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String userGoal;
    @Getter @Setter
    private Goal goal;

    @Getter private Calendar startTime;
    @Getter private Calendar endTime;
    @Getter private int days;
    @Getter private final LinkedList<Poll> polls = new LinkedList<>();

    public Course(String name, Calendar startTime) {
        this.startTime = startTime;
        this.name = name;
    }

    public Course() { }

    public void calculateEnd(int month) {
        endTime = new GregorianCalendar();
        endTime.add(Calendar.MONTH, month);

        days = DateUtils.getDaysBetween(startTime, endTime);
    }
}