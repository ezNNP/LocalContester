package ru.stray27.simplecontester.backend.taskchecker.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.stray27.simplecontester.backend.taskchecker.controller.dto.RunResponse;
import ru.stray27.simplecontester.backend.taskchecker.controller.dto.RunTestsRequest;
import ru.stray27.simplecontester.backend.taskchecker.controller.dto.RunTestsResponse;
import ru.stray27.simplecontester.backend.taskchecker.controller.dto.TestDto;
import ru.stray27.simplecontester.backend.taskchecker.repository.TestRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TestFinderServiceImpl implements TestFinderService {

    private final TestRepository testRepository;

    @Override
    public List<RunTestsResponse> findAllTestsByUsername(String username) {
        return testRepository.findAllByUsername(username).stream().map((test) -> RunTestsResponse.builder()
                .id(test.getId())
                .taskId(test.getTaskId())
                .username(test.getUsername())
                .language(test.getLanguage())
                .testStatus(test.getStatus())
                .languageVersion(test.getLanguageVersion())
                .testsPassed(test.getTestNumber())
                .build()).collect(Collectors.toList());
    }
}
