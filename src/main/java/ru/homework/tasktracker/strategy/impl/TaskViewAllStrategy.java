package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.TaskViewAllArgument;

import java.util.List;

import static ru.homework.tasktracker.strategy.mapper.TaskStrategyArgumentMapper.toTaskViewAllArgument;
import static ru.homework.tasktracker.util.MessageHelperUtil.createMessageFromListOfEntities;

@Component
@RequiredArgsConstructor
public class TaskViewAllStrategy implements Strategy {
    private final TaskService taskService;

    @Override
    public StrategyResponse execute(String argument) {
        TaskViewAllArgument taskViewAllArgument = toTaskViewAllArgument(argument);
        List<Task> tasks = taskService.findAll(taskViewAllArgument.getTaskFilter());
        return new StrategyResponse(createMessageFromListOfEntities(
                "Список задач: \n",
                "Задач в бд вообще нет",
                tasks));
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.TASK_VIEW_ALL;
    }
}
