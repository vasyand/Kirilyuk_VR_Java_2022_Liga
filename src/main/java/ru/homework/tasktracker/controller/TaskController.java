package ru.homework.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.homework.tasktracker.executor.StrategyExecutor;
import ru.homework.tasktracker.model.StrategyResponse;

import static ru.homework.tasktracker.model.StrategyName.*;
import static ru.homework.tasktracker.model.StrategyName.USER_WITH_MAX_NUMBER_TASKS;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/tasks")
public class TaskController {
    private final StrategyExecutor strategyExecutor;

    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@PathVariable String id) {
        StrategyResponse response = strategyExecutor.executeStrategy(TASK_VIEW, id);
        return new ResponseEntity<>(response.getMessage(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> findAll(@RequestParam(required = false) String filters) {
        StrategyResponse response = strategyExecutor.executeStrategy(TASK_VIEW_ALL, filters);
        return new ResponseEntity<>(response.getMessage(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody String args) {
        StrategyResponse response = strategyExecutor.executeStrategy(TASK_CREATE, args);
        return new ResponseEntity<>(response.getMessage(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody String args) {
        StrategyResponse response = strategyExecutor.executeStrategy(TASK_EDIT, id + " " + args);
        return new ResponseEntity<>(response.getMessage(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        StrategyResponse response = strategyExecutor.executeStrategy(TASK_DELETE, id);
        return new ResponseEntity<>(response.getMessage(), HttpStatus.OK);
    }
}
