package ru.stray27.simplecontester.backend.taskstore.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stray27.simplecontester.backend.taskstore.controller.dto.TaskCreateRequest;
import ru.stray27.simplecontester.backend.taskstore.controller.dto.TaskDto;
import ru.stray27.simplecontester.backend.taskstore.controller.dto.TestDto;
import ru.stray27.simplecontester.backend.taskstore.exception.TaskNotFoundException;
import ru.stray27.simplecontester.backend.taskstore.exception.TaskValidationException;
import ru.stray27.simplecontester.backend.taskstore.model.Task;
import ru.stray27.simplecontester.backend.taskstore.model.TaskTest;
import ru.stray27.simplecontester.backend.taskstore.repository.TaskRepository;
import ru.stray27.simplecontester.backend.taskstore.repository.TaskTestRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskManageServiceImpl implements TaskManageService {

    private TaskRepository taskRepository;
    private TaskTestRepository taskTestRepository;

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream().map((task) -> TaskDto.builder().id(task.getId()).title(task.getTitle()).description(task.getDescription()).build()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Task saveTaskFromRequest(TaskCreateRequest request) {
        if (isRequestValid(request)) {
            Task task = taskRepository.save(Task.builder()
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .build()
            );

            saveTests(task, request.getTests());

            return task;
        } else {
            throw new TaskValidationException();
        }
    }

    @Override
    @Transactional
    public Task updateTaskFromRequest(TaskDto request) {
        if (isRequestValid(request)) {
            Task task = taskRepository.findById(request.getId()).orElseThrow(TaskNotFoundException::new);
            task.setTitle(request.getTitle());
            task.setDescription(request.getDescription());
            taskTestRepository.deleteAllByTaskId(task.getId());

            saveTests(task, request.getTests());

            return taskRepository.save(task);
        } else {
            throw new TaskValidationException();
        }
    }

    @Override
    @Transactional
    public void deleteTaskById(Long id) {
        taskTestRepository.deleteAllByTaskId(id);
        taskRepository.deleteById(id);
    }

    @Override
    public TaskDto getTaskDtoWithVisibleTestsById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        return TaskDto.builder()
                .id(id)
                .title(task.getTitle())
                .description(task.getDescription())
                .tests(task.getTests().stream().filter(TaskTest::getVisible).map(t -> TestDto.builder()
                        .input(t.getInput())
                        .expectedOutput(t.getExpectedOutput())
                        .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public TaskDto getTaskDtoWithAllTestsById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        return TaskDto.builder()
                .id(id)
                .title(task.getTitle())
                .description(task.getDescription())
                .tests(task.getTests().stream().map(t ->
                                TestDto.builder()
                        .input(t.getInput())
                        .expectedOutput(t.getExpectedOutput())
                        .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private boolean isRequestValid(TaskCreateRequest request) {
        return !request.getTitle().isEmpty() && !request.getDescription().isEmpty() && !request.getTests().isEmpty();
    }

    private boolean isRequestValid(TaskDto request) {
        return !(request.getId() == null) && !request.getTitle().isEmpty() && !request.getDescription().isEmpty() && !request.getTests().isEmpty();
    }

    private void saveTests(Task task, List<TestDto> tests) {
        tests.forEach(tr -> taskTestRepository.save(
                TaskTest.builder()
                        .input(tr.getInput())
                        .expectedOutput(tr.getExpectedOutput())
                        .visible(tr.getVisible())
                        .task(task)
                        .build()
        ));
    }
}
