package ru.stray27.simplecontester.backend.taskchecker.model;

public enum TestStatus {
    QUEUED,
    RUNNING,
    SUCCESS,
    WRONG_ANSWER,
    RUNTIME_ERROR,
    COMPILE_TIME_ERROR,
    TIME_LIMIT_EXCEED,
    MEMORY_LIMIT_EXCEED,
    INTERNAL_ERROR
}
