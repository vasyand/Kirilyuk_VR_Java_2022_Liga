package ru.homework.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.homework.tasktracker.executor.StrategyExecutor;
import ru.homework.tasktracker.model.StrategyResponse;

import static ru.homework.tasktracker.model.StrategyName.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/projects")
public class ProjectController {
    private final StrategyExecutor strategyExecutor;

    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@PathVariable String id, @RequestParam String args) {
        StrategyResponse response = strategyExecutor.executeStrategy(PROJECT_VIEW, id + " " + args);
        return new ResponseEntity<>(response.getMessage(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> findAll(@RequestParam String filters) {
        StrategyResponse response = strategyExecutor.executeStrategy(PROJECT_VIEW_ALL, filters);
        return new ResponseEntity<>(response.getMessage(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody String args) {
        StrategyResponse response = strategyExecutor.executeStrategy(PROJECT_CREATE, args);
        return new ResponseEntity<>(response.getMessage(), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody String args) {
        StrategyResponse response = strategyExecutor.executeStrategy(PROJECT_EDIT, args);
        return new ResponseEntity<>(response.getMessage(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        StrategyResponse response = strategyExecutor.executeStrategy(PROJECT_DELETE, id);
        return new ResponseEntity<>(response.getMessage(), HttpStatus.OK);
    }
}
