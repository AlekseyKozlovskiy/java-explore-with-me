package ru.practicum.explore.exceptions;

public class IncorrectRequest extends RuntimeException {
    public IncorrectRequest(String s) {
        super(s);
    }
}
