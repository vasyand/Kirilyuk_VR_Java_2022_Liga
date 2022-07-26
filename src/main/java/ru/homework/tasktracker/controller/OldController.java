package ru.homework.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.homework.tasktracker.executor.StrategyExecutor;
import ru.homework.tasktracker.model.StrategyResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/api/v1")
public class OldController {
    private final StrategyExecutor strategyExecutor;

    @GetMapping
    public ResponseEntity<?> executeCommand(@RequestParam("event") String event) {
        StrategyResponse strategyResponse = strategyExecutor.executeStrategy(event);
        return new ResponseEntity<>(strategyResponse.getMessage(),HttpStatus.OK);
    }

    @GetMapping("/users/search-with-max-number-tasks")
    public ResponseEntity<?> searchUserWithMaxNumberTasks(@RequestParam("event") String event) {
        StrategyResponse strategyResponse = strategyExecutor.executeStrategy(event);
        return new ResponseEntity<>(strategyResponse.getMessage(), HttpStatus.OK);
    }
}
