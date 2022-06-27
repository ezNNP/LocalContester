package ru.stray27.simplecontester.backend.runner.controller;

import com.github.codeboy.piston4j.api.CodeFile;
import com.github.codeboy.piston4j.api.ExecutionRequest;
import com.github.codeboy.piston4j.api.ExecutionResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.simplecontester.backend.runner.controller.dto.RunRequest;
import ru.stray27.simplecontester.backend.runner.controller.dto.RunResponse;
import ru.stray27.simplecontester.backend.runner.exceptions.NoSpecifiedLanguageException;
import ru.stray27.simplecontester.backend.runner.model.RunInstance;
import ru.stray27.simplecontester.backend.runner.repository.RunInstanceRepository;
import ru.stray27.simplecontester.backend.runner.service.RunnerService;

import javax.annotation.security.RolesAllowed;

@Slf4j
@RestController
@RequestMapping("/api/runner")
@AllArgsConstructor
public class RunController {

    private RunInstanceRepository repository;
    private RunnerService runnerService;

    @GetMapping("/languages")
    @RolesAllowed({"User", "Admin"})
    public ResponseEntity<?> getLanguages() {
        try {
            return new ResponseEntity<>(runnerService.getLanguages(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error in getLanguages", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/run")
    @RolesAllowed({"User", "Admin"})
    public ResponseEntity<?> run(@RequestBody RunRequest runRequest) {
        try {
            if (!validateRunRequest(runRequest)) {
                throw new RuntimeException("Invalid request");
            }
            log.info("Running request from user: {}", runRequest.getUsername());
            RunInstance runInstance = repository.save(prepareRunInstance(runRequest));
            ExecutionResult result = runnerService.runTest(prepareExecutionRequest(runRequest));
            RunResponse response = RunResponse.builder()
                    .id(runInstance.getId())
                    .username(runRequest.getUsername())
                    .language(runRequest.getLanguage())
                    .version(runRequest.getVersion())
                    .stdout(result.getOutput().getStdout().trim())
                    .stderr(result.getOutput().getStderr().trim())
                    .code(result.getOutput().getCode())
                    .build();
            runInstance.setStdout(response.getStdout());
            runInstance.setStderr(response.getStderr());
            runInstance.setCode(response.getCode());
            repository.save(runInstance);
            log.info("Finishing request from user: {}", runInstance.getUsername());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoSpecifiedLanguageException e) {
            log.warn("{} languages doesn't exist", runRequest.getLanguage());
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error in run", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/run/{id}")
    @RolesAllowed({"User", "Admin"})
    public ResponseEntity<?> getRunInstanceById(@PathVariable String id) {
        try {
            RunInstance runInstance = repository.findById(Long.parseLong(id)).orElseThrow(() -> new RuntimeException("Id not found"));
            RunResponse response = RunResponse.builder()
                    .id(runInstance.getId())
                    .username(runInstance.getUsername())
                    .language(runInstance.getLanguage())
                    .version(runInstance.getVersion())
                    .stdout(runInstance.getStdout())
                    .stderr(runInstance.getStderr())
                    .code(runInstance.getCode())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn("May be error in getRunInstanceById", e);
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error in getRunInstanceById", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean validateRunRequest(RunRequest runRequest) {
        return runRequest != null &&
                runRequest.getUsername() != null &&
                runRequest.getLanguage() != null &&
                runRequest.getVersion() != null &&
                runRequest.getStdin() != null &&
                runRequest.getSourceCode() != null;
    }

    private RunInstance prepareRunInstance(RunRequest runRequest) {
        return RunInstance.builder()
                .username(runRequest.getUsername())
                .language(runRequest.getLanguage())
                .version(runRequest.getVersion())
                .build();
    }

    private ExecutionRequest prepareExecutionRequest(RunRequest runRequest) {
        ExecutionRequest executionRequest = new ExecutionRequest(runRequest.getLanguage(), runRequest.getVersion(), new CodeFile(runRequest.getSourceCode()));
        executionRequest.setStdin(runRequest.getStdin());
        return executionRequest;
    }

}
