package ru.siaw.database;

import lombok.Getter;
import lombok.Setter;
import ru.siaw.motivation.Difficulties;
import ru.siaw.motivation.Easily;
import ru.siaw.motivation.Emotion;
import ru.siaw.motivation.Motivation;

/** Объект обозначает один "опрос" студента в определенный день обучения */
public class Poll {
    @Getter private int day;
    @Getter @Setter private String important; // То, что студенту важно / надо выучить
    @Getter @Setter private boolean notEnoughInfo = false; // Студенту не хватило информации на курсе
    @Getter @Setter private String learn; // То, что не понятно студенту if (notEnoughInfo == true) learn != null
    @Getter @Setter private Emotion emotion; // Эмоциональное состояние
    @Getter @Setter private Difficulties difficulties; // Сложности в обучении
    @Getter @Setter private Easily easily; // То, что было легко в обучении
    @Getter @Setter private Motivation motivation; // Мотивация

    public Poll(int day) {
        this.day = day;
    }

    public Poll() { }
}
