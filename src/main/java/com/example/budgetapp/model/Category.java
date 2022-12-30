package com.example.budgetapp.model;

public enum Category {
    FOOD("Еда"),
    CLOTHES("Одежда"),
    FUN("Развлечения"),
    TRANSPORT("Транспорт"),
    HOBBY("Хобби");
    private String text;

    Category(String text) {
        this.text = text;
    }

    public final String getText() {
        return text;
    }
}
