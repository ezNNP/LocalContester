package ru.stray27.simplecontester.backend.runner.service;

import com.github.codeboy.piston4j.api.ExecutionRequest;
import com.github.codeboy.piston4j.api.ExecutionResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

public interface RunnerService {
    ExecutionResult runTest(ExecutionRequest executionRequest);
    List<String> getLanguages();
}
