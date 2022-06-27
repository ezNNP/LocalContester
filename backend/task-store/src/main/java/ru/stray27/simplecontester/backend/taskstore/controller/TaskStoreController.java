package ru.stray27.simplecontester.backend.taskstore.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.simplecontester.backend.taskstore.controller.dto.TaskCreateRequest;
import ru.stray27.simplecontester.backend.taskstore.controller.dto.TaskDto;
import ru.stray27.simplecontester.backend.taskstore.exception.TaskNotFoundException;
import ru.stray27.simplecontester.backend.taskstore.exception.TaskValidationException;
import ru.stray27.simplecontester.backend.taskstore.service.TaskManageService;

import javax.annotation.security.RolesAllowed;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/task")
public class TaskStoreController {

    private TaskManageService taskManageService;

    @PostMapping("/create")
    @RolesAllowed({"Admin"})
    public ResponseEntity<?> createTask(@RequestBody TaskCreateRequest request) {
        try {
            Long taskId = taskManageService.saveTaskFromRequest(request).getId();
            return new ResponseEntity<>(taskId, HttpStatus.OK);
        } catch (TaskValidationException e) {
            log.warn("Validation in update task is not successful");
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error while handling create request", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
    @RolesAllowed({"Admin"})
    public ResponseEntity<?> updateTask(@RequestBody TaskDto request) {
        try {
            Long taskId = taskManageService.updateTaskFromRequest(request).getId();
            return new ResponseEntity<>(taskId, HttpStatus.OK);
        } catch (TaskValidationException e) {
            log.warn("Validation in update task is not successful");
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error while handling update request");
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    @RolesAllowed({"User", "Admin"})
    public ResponseEntity<?> getAllTasks() {
        try {
            return new ResponseEntity<>(taskManageService.getAllTasks(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    @RolesAllowed({"User", "Admin"})
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        try {
            TaskDto taskDto = taskManageService.getTaskDtoWithVisibleTestsById(id);
            return new ResponseEntity<>(taskDto, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            log.warn("Task with id {} not found", id);
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error handling getTaskById request. Something wrong with id {}", id);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}/all")
    @RolesAllowed({"Admin", "User"})
    public ResponseEntity<?> getTaskWithAllTestsByTaskId(@PathVariable Long id) {
        try {
            TaskDto taskDto = taskManageService.getTaskDtoWithAllTestsById(id);
            return new ResponseEntity<>(taskDto, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            log.warn("Task with id {} not found", id);
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error handling getTaskWithAllTestsByTaskId request. Something wrong with id {}", id);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    @RolesAllowed({"Admin"})
    public ResponseEntity<?> deleteTaskById(@PathVariable Long id) {
        try {
            taskManageService.deleteTaskById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while deleting task with id {}", id);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
