package ru.stray27.simplecontester.backend.taskstore.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestDto {
    private String input;
    private String expectedOutput;
    private Boolean visible;
}
