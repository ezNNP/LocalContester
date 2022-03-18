package ru.stray27.simplecontester.backend.taskchecker.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.stray27.simplecontester.backend.taskchecker.model.TestStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RunTestsResponse {
    private Long id;
    private Long taskId;
    private String username;
    private String language;
    private String languageVersion;
    private TestStatus testStatus;
    private Long testsPassed;
}
