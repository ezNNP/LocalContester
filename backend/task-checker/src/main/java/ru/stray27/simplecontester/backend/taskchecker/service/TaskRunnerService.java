package ru.stray27.simplecontester.backend.taskchecker.service;

import ru.stray27.simplecontester.backend.taskchecker.controller.dto.RunTestsRequest;

public interface TaskRunnerService {
    Long runTests(RunTestsRequest request);
}
