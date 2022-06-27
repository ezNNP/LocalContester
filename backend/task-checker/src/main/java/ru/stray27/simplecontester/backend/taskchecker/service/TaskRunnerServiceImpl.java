package ru.stray27.simplecontester.backend.taskchecker.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.stray27.simplecontester.backend.taskchecker.controller.dto.*;
import ru.stray27.simplecontester.backend.taskchecker.model.Test;
import ru.stray27.simplecontester.backend.taskchecker.model.TestStatus;
import ru.stray27.simplecontester.backend.taskchecker.repository.TestRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Slf4j
@Service
@AllArgsConstructor
public class TaskRunnerServiceImpl implements TaskRunnerService {

    private TestRepository testRepository;
    private ExecutorService executorService;
    private TestMarkerService testMarkerService;
    private RestTemplate restTemplate;

    private final String URL_RUNNER_RUN_REQUEST = "http://RUNNER/api/runner/run";
    private final String URL_TASK_STORE_GET_TESTS_REQUEST_FORMAT = "http://TASKSTORE/api/task/get/%d/all";

    @Override
    public Long runTests(RunTestsRequest request, String token) {
        Test testEntity = saveTest(request);

        executorService.submit(() -> {
            try {
                log.info("Started executing test with id {}. Task id: {}. Username: {}", testEntity.getId(), testEntity.getTaskId(), testEntity.getUsername());
                boolean testsSuccess = true;

                List<TestDto> tests = getTestsByTaskId(token, request.getTaskId());

                for (TestDto singleTest : tests) {
                    RunResponse runResponse = sendSingleRunRequest(request, singleTest, token);
                    TestStatus currentStatus = handleSingleRunResponse(testEntity, singleTest, runResponse);
                    if (currentStatus != TestStatus.RUNNING) {
                        testsSuccess = false;
                        break;
                    }
                }

                if (testsSuccess) {
                    testMarkerService.markSuccess(testEntity);
                }
                log.info("Finishing executing test with id {}. Task id: {}. Username: {}", testEntity.getId(), testEntity.getTaskId(), testEntity.getUsername());
            } catch (Exception e) {
                testMarkerService.markInternalError(testEntity);
                log.error("Error executing test with id {}. Task id: {}. Username: {}", testEntity.getId(), testEntity.getTaskId(), testEntity.getUsername());
                log.error("Error:", e);
            }
        });

        return testEntity.getId();
    }

    private List<TestDto> getTestsByTaskId(String token, Long taskId) {
        String url = String.format(URL_TASK_STORE_GET_TESTS_REQUEST_FORMAT, taskId);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", token);
        ResponseEntity<TaskDto> taskDtoResponseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), TaskDto.class);
        return taskDtoResponseEntity.getBody().getTests();
    }

    private TestStatus handleSingleRunResponse(Test testEntity, TestDto singleTest, RunResponse runResponse) {
        if (runResponse.getStderr().isEmpty()) {
            String expectedOutput = singleTest.getExpectedOutput();
            if (expectedOutput.equals(runResponse.getStdout())) {
                testMarkerService.incrementTestCount(testEntity);
                return TestStatus.RUNNING;
            } else {
                testMarkerService.markWrongAnswer(testEntity);
                return TestStatus.WRONG_ANSWER;
            }
        } else {
            testMarkerService.markRuntimeError(testEntity);
            return TestStatus.RUNTIME_ERROR;
        }
    }

    private RunResponse sendSingleRunRequest(RunTestsRequest request, TestDto singleTest, String token) {
        RunRequest runRequest = RunRequest.builder()
                .username(request.getUsername())
                .language(request.getLanguage())
                .version(request.getLanguageVersion())
                .sourceCode(request.getSourceCode())
                .stdin(singleTest.getInput())
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        return restTemplate.exchange(URL_RUNNER_RUN_REQUEST, HttpMethod.POST, new HttpEntity<>(runRequest, headers), RunResponse.class).getBody();
    }

    private Test saveTest(RunTestsRequest request) {
        Test testEntity = getEntity(request);
        testEntity = testRepository.save(testEntity);
        return testEntity;
    }

    private Test getEntity(RunTestsRequest request) {
        return Test.builder()
                .taskId(request.getTaskId())
                .status(TestStatus.QUEUED)
                .language(request.getLanguage())
                .languageVersion(request.getLanguageVersion())
                .username(request.getUsername())
                .testNumber(0L)
                .build();
    }
}
