package ru.practicum.utils;

import java.time.format.DateTimeFormatter;

public class FormatterDate {
    public static DateTimeFormatter formatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    }
}
