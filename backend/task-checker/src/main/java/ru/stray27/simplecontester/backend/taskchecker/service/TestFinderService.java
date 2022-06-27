package ru.stray27.simplecontester.backend.taskchecker.service;

import ru.stray27.simplecontester.backend.taskchecker.controller.dto.RunResponse;
import ru.stray27.simplecontester.backend.taskchecker.controller.dto.RunTestsResponse;
import ru.stray27.simplecontester.backend.taskchecker.controller.dto.TestDto;

import java.util.List;

public interface TestFinderService {
    List<RunTestsResponse> findAllTestsByUsername(String username);
}
