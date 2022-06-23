package ru.siaw.motivation;

public enum Goal {
    COMPETENCE("Повышение уровня компетенций"),
    STATUS("Получение статуса"),
    INCOME("Увеличение дохода / зарплаты"),
    NEW_SPHERE("Переход в новую сферу"),
    SELF_DEVELOPMENT("Саморазвитие"),
    STUDY_FOR_COMPANY("За компанию учусь"),
    QUALIFICATION("Повышаю квалификацию от работы"),
    EVERYTHING_CHANGE("Проснулся и понял, что все нужно менять"),
    RAISING("Хочу повышение на работе");

    public String text;

    Goal(String text) {
        this.text = text;
    }
}