package ru.siaw.motivation;

public enum Emotion {
    BEST_OF_ALL("\uD83D\uDE0D", "счастье", true),
    GREAT("\uD83D\uDE04", "радость",true),
    WELL("\uD83D\uDE0A", "все хорошо",true),
    BADLY("\uD83D\uDE41", "скука",false),
    TERRIBLY("\uD83D\uDCA9", "все плохо",false),
    VERY_BAD("\uD83D\uDE21", "в гневе",false);

    public final String text;
    public final String shortName;
    public final boolean isPositive;

    Emotion(String text, String shortName, boolean isPositive) {
        this.text = text;
        this.shortName = shortName;
        this.isPositive = isPositive;
    }
}