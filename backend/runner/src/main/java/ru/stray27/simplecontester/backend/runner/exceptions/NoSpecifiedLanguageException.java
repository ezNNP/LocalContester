package ru.stray27.simplecontester.backend.runner.exceptions;

public class NoSpecifiedLanguageException extends IllegalArgumentException {
    public NoSpecifiedLanguageException(String s) {
        super(s);
    }
}
