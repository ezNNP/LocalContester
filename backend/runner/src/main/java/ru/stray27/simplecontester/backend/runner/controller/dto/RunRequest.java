package ru.stray27.simplecontester.backend.runner.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RunRequest {
    private String username;
    private String language;
    private String version;
    private String sourceCode;
    private String stdin;
}
