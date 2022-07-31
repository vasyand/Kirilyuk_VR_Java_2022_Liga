package ru.homework.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.homework.tasktracker.model.dto.TaskFullDto;
import ru.homework.tasktracker.model.dto.TaskPostDto;
import ru.homework.tasktracker.model.filter.TaskFilter;
import ru.homework.tasktracker.service.TaskService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<TaskFullDto> findById(@PathVariable Long id) {
        TaskFullDto task = taskService.findById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<TaskFullDto>> findAll(@RequestBody TaskFilter taskFilter, Pageable pageable) {
        Page<TaskFullDto> tasks = taskService.findAll(taskFilter, pageable);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody TaskPostDto taskPostDto) {
        Long id = taskService.save(taskPostDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody TaskPostDto taskPostDto, @PathVariable Long id) {
        taskService.update(taskPostDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        taskService.delete(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
