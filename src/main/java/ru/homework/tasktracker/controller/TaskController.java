package ru.homework.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.homework.tasktracker.model.dto.TaskFullDto;
import ru.homework.tasktracker.model.dto.TaskCreateDto;
import ru.homework.tasktracker.model.dto.TaskUpdateDto;
import ru.homework.tasktracker.model.filter.TaskFilter;
import ru.homework.tasktracker.service.TaskService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<TaskFullDto> findById(@PathVariable Long id) {
        TaskFullDto task = taskService.findById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<TaskFullDto>> findAll(@RequestBody(required = false) TaskFilter taskFilter,
                                                     Pageable pageable) {
        Page<TaskFullDto> tasks = taskService.findAll(taskFilter, pageable);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TaskFullDto> create(@RequestBody @Valid TaskCreateDto taskCreateDto) {
        TaskFullDto taskFullDto = taskService.save(taskCreateDto);
        return new ResponseEntity<>(taskFullDto, HttpStatus.CREATED);
    }

    @PreAuthorize("@userValidator.thisTaskBelongToUser(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody TaskUpdateDto taskUpdateDto,
                                    @PathVariable Long id) {
        taskService.update(taskUpdateDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("@userValidator.thisTaskBelongToUser(#id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        taskService.delete(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
