package ru.stray27.simplecontester.backend.taskstore.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskCreateRequest {
    //TODO: Merge with TaskDto and id = NULL when creating new task.
    private String title;
    private String description;
    private List<TestDto> tests;
}
