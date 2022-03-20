package ru.stray27.simplecontester.backend.runner.service;

import com.github.codeboy.piston4j.api.CodeFile;
import com.github.codeboy.piston4j.api.ExecutionRequest;
import com.github.codeboy.piston4j.api.ExecutionResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RunnerServiceImplTest {

    @Autowired
    private RunnerService runnerService;

    @Test
    void runTest() {
        String sourceCode = "public class Main {" +
                "public static void main(String[] args) {" +
                "System.out.print(\"Hello world\");" +
                "}" +
                "}";
        ExecutionRequest executionRequest = new ExecutionRequest("java", "15.0.2", new CodeFile(sourceCode));
        ExecutionResult result = runnerService.runTest(executionRequest);

        Assertions.assertEquals("Hello world", result.getOutput().getStdout());
    }
}