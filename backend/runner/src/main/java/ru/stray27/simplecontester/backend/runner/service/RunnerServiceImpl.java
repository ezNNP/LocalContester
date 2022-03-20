package ru.stray27.simplecontester.backend.runner.service;

import com.github.codeboy.piston4j.api.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stray27.simplecontester.backend.runner.exceptions.NoSpecifiedLanguageException;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RunnerServiceImpl implements RunnerService {

    private Piston piston;

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
