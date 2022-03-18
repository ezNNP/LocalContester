package ru.stray27.simplecontester.backend.taskchecker.service;

import org.springframework.stereotype.Service;
import ru.stray27.simplecontester.backend.taskchecker.controller.dto.RunTestsResponse;

@Service
public interface TaskFinderService {
    RunTestsResponse findTaskById(Long id);
}
