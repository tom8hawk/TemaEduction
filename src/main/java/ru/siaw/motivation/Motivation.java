package ru.siaw.motivation;

public enum Motivation {
    PEAK("Я на пике, готов свернуть горы"),
    ITS_OKAY("Все окей, идем дальше"),
    DEPRESSED("Что-то я приуныл"),
    HARD("Хочу все бросить"),
    AT_THE_BOTTOM("Кажется, я на дне");

    public String text;

    Motivation(String text) {
        this.text = text;
    }
}
