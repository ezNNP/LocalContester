package ru.stray27.simplecontester.backend.taskchecker.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.stray27.simplecontester.backend.taskchecker.controller.dto.RunTestsRequest;
import ru.stray27.simplecontester.backend.taskchecker.controller.dto.RunTestsResponse;
import ru.stray27.simplecontester.backend.taskchecker.service.TaskFinderService;
import ru.stray27.simplecontester.backend.taskchecker.service.TaskRunnerService;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/checker")
public class TaskCheckerController {

    private TaskRunnerService taskRunnerService;
    private TaskFinderService taskFinderService;

    @PostMapping("/run")
    public ResponseEntity<?> runChecker(@RequestBody RunTestsRequest request) {
        try {
            Long id = taskRunnerService.runTests(request);
            RunTestsResponse response = RunTestsResponse.builder()
                    .id(id)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while running tests", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getInformationById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(taskFinderService.findTaskById(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Internal error", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
