package ru.siaw.motivation;

public enum Difficulties {
    HARD_TO_LISTEN("Слушать, ничего не понимаю", "слушать"),
    EYES_HURT("От картинок глаза болят", "смотреть"),
    CANT_READ("Нет настроения читать", "читать"),
    NO_IDEAS("Не  могу писать, ноль идей", "писать"),
    NO_PRACTICE("Никакой практики", "использовать"),
    ALL_RIGHT("Все хорошо", "");

    public final String text;
    public final String shortName;

    Difficulties(String text, String shortName) {
        this.text = text;
        this.shortName = shortName;
    }
}
