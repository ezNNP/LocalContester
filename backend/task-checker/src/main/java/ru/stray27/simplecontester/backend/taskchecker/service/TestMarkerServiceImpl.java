package ru.stray27.simplecontester.backend.taskchecker.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stray27.simplecontester.backend.taskchecker.model.Test;
import ru.stray27.simplecontester.backend.taskchecker.model.TestStatus;
import ru.stray27.simplecontester.backend.taskchecker.repository.TestRepository;

@Service
@AllArgsConstructor
public class TestMarkerServiceImpl implements TestMarkerService {

    private TestRepository testRepository;

    @Override
    public Test markQueued(Test test) {
        test.setStatus(TestStatus.QUEUED);
        return testRepository.save(test);
    }

    @Override
    public Test markRunning(Test test) {
        test.setStatus(TestStatus.RUNNING);
        return testRepository.save(test);
    }

    @Override
    public Test markSuccess(Test test) {
        test.setStatus(TestStatus.SUCCESS);
        return testRepository.save(test);
    }

    @Override
    public Test markWrongAnswer(Test test) {
        test.setStatus(TestStatus.WRONG_ANSWER);
        return testRepository.save(test);
    }

    @Override
    public Test markRuntimeError(Test test) {
        test.setStatus(TestStatus.RUNTIME_ERROR);
        return testRepository.save(test);
    }

    @Override
    public Test markCompileTimeError(Test test) {
        test.setStatus(TestStatus.COMPILE_TIME_ERROR);
        return testRepository.save(test);
    }

    @Override
    public Test markTimeLimitExceed(Test test) {
        test.setStatus(TestStatus.TIME_LIMIT_EXCEED);
        return testRepository.save(test);
    }

    @Override
    public Test markMemoryLimitExceed(Test test) {
        test.setStatus(TestStatus.MEMORY_LIMIT_EXCEED);
        return testRepository.save(test);
    }

    @Override
    public Test markInternalError(Test test) {
        test.setStatus(TestStatus.INTERNAL_ERROR);
        return testRepository.save(test);
    }

    @Override
    public Test incrementTestCount(Test test) {
        test.setTestNumber(test.getTestNumber() + 1);
        return testRepository.save(test);
    }
}
