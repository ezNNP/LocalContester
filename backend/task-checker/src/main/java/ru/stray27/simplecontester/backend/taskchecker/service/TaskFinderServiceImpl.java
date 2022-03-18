package ru.stray27.simplecontester.backend.taskchecker.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stray27.simplecontester.backend.taskchecker.controller.dto.RunTestsResponse;
import ru.stray27.simplecontester.backend.taskchecker.exception.TaskNotFoundException;
import ru.stray27.simplecontester.backend.taskchecker.model.Test;
import ru.stray27.simplecontester.backend.taskchecker.model.TestStatus;
import ru.stray27.simplecontester.backend.taskchecker.repository.TestRepository;

@AllArgsConstructor
@Service
public class TaskFinderServiceImpl implements TaskFinderService {

    private TestRepository testRepository;

    @Override
    public RunTestsResponse findTaskById(Long id) {
        Test test = testRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        return RunTestsResponse.builder()
                .id(test.getId())
                .taskId(test.getTaskId())
                .username(test.getUsername())
                .language(test.getLanguage())
                .languageVersion(test.getLanguageVersion())
                .testStatus(test.getStatus())
                .testsPassed(test.getStatus() == TestStatus.SUCCESS ? 0 : test.getTestNumber())
                .build();
    }
}
