package ru.homework.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.homework.tasktracker.executor.StrategyExecutor;

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/api/v2/users")
public class UserController {
    private final StrategyExecutor strategyExecutor;
}
