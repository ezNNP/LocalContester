package ru.stray27.simplecontester.backend.runner.service;

import com.github.codeboy.piston4j.api.*;
import com.github.codeboy.piston4j.api.Runtime;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stray27.simplecontester.backend.runner.exceptions.NoSpecifiedLanguageException;
import ru.stray27.simplecontester.backend.runner.repository.RunInstanceRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RunnerServiceImpl implements RunnerService {

    private Piston piston;
    private RunInstanceRepository runInstanceRepository;
    private ExecutorService executorService;

    @Override
    public ExecutionResult runTest(ExecutionRequest executionRequest) {
        piston.getRuntime(executionRequest.getLanguage()).orElseThrow(() -> new NoSpecifiedLanguageException("Can't find specified language"));
        return piston.execute(executionRequest);
    }

    @Override
    public List<String> getLanguages() {
        return piston.getRuntimes().stream()
                .map(runtime -> String.join(":", runtime.getLanguage(), runtime.getVersion()))
                .collect(Collectors.toList());
    }
}
