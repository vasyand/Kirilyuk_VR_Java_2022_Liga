package ru.homework.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.homework.tasktracker.executor.StrategyExecutor;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final StrategyExecutor strategyExecutor;


    @GetMapping
    public ResponseEntity<?> executeCommand(@RequestParam("event") String event) {
        strategyExecutor.executeEvent(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
