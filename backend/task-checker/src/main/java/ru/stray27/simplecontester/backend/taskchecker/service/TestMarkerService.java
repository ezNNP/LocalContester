package ru.stray27.simplecontester.backend.taskchecker.service;

import ru.stray27.simplecontester.backend.taskchecker.model.Test;

public interface TestMarkerService {
    Test markQueued(Test test);
    Test markRunning(Test test);
    Test markSuccess(Test test);
    Test markWrongAnswer(Test test);
    Test markRuntimeError(Test test);
    Test markCompileTimeError(Test test);
    Test markTimeLimitExceed(Test test);
    Test markMemoryLimitExceed(Test test);
    Test markInternalError(Test test);
    Test incrementTestCount(Test test);
}
