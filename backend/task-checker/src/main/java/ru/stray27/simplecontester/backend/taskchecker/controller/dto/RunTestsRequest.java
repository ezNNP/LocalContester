package ru.stray27.simplecontester.backend.taskchecker.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RunTestsRequest {
    private Long taskId;
    private String username;
    private String sourceCode;
    private String language;
    private String languageVersion;
}
