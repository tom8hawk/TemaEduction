package ru.siaw.motivation;

public enum Easily {
    LIKE_TO_LISTEN("Люблю слушать", "слушать"),
    VIDEOS_AND_GRAPHS("Картинки - все понятно", "смотреть"),
    READ("Много букв - это мое", "читать"),
    WRITE_TEXT("Писать это проще простого", "писать"),
    PRACTICE("Больше практики", "использовать"),
    DIFFICULT("Все сегодня сложно", "");

    public final String text;
    public final String shortText;

    Easily(String text, String shortText) {
        this.text = text;
        this.shortText = shortText;
    }
}
