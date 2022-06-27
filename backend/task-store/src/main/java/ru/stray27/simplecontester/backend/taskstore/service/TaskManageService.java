package ru.stray27.simplecontester.backend.taskstore.service;

import ru.stray27.simplecontester.backend.taskstore.controller.dto.TaskCreateRequest;
import ru.stray27.simplecontester.backend.taskstore.controller.dto.TaskDto;
import ru.stray27.simplecontester.backend.taskstore.model.Task;

import java.util.List;

public interface TaskManageService {

    List<TaskDto> getAllTasks();
    Task saveTaskFromRequest(TaskCreateRequest request);
    Task updateTaskFromRequest(TaskDto request);
    void deleteTaskById(Long id);
    TaskDto getTaskDtoWithVisibleTestsById(Long id);
    TaskDto getTaskDtoWithAllTestsById(Long id);
}
