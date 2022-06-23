package ru.siaw.database;

import lombok.Getter;
import lombok.Setter;

import java.util.GregorianCalendar;

public class User {
    @Getter private long id;
    @Getter @Setter private String name;
    @Getter @Setter private Course course;

    public User(long id) {
        this.id = id;
    }

    public User() { }

    public Course newCourse(String name) {
        course = new Course(name, new GregorianCalendar());
        return course;
    }
}