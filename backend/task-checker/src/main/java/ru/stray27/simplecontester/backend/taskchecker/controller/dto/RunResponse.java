package ru.stray27.simplecontester.backend.taskchecker.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RunResponse {
    private Long id;
    private String username;
    private String language;
    private String version;
    private String stdout;
    private String stderr;
    private Integer code;
}
