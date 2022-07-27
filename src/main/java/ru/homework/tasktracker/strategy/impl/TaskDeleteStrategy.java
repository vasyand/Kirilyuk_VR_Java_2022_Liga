package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.TaskDeleteArgument;

import static ru.homework.tasktracker.strategy.mapper.TaskStrategyArgumentMapper.toTaskDeleteArgument;

@Component
@RequiredArgsConstructor
public class TaskDeleteStrategy implements Strategy {
    private final TaskService taskService;

    @Override
    public StrategyResponse execute(String argument) {
        TaskDeleteArgument taskDeleteArgument = toTaskDeleteArgument(argument);
        taskService.delete(taskDeleteArgument.getTaskId());
        return new StrategyResponse("Задача успешно удалена!");
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.TASK_DELETE;
    }
}
