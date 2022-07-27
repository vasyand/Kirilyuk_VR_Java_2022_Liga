package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.TaskViewArgument;

import static ru.homework.tasktracker.strategy.mapper.TaskStrategyArgumentMapper.toTaskViewArgument;
import static ru.homework.tasktracker.util.MessageHelperUtil.createMessageFromEntity;

@Component
@RequiredArgsConstructor
public class TaskViewStrategy implements Strategy {

    private final TaskService taskService;

    @Override
    public StrategyResponse execute(String argument) {
        TaskViewArgument taskViewArgument = toTaskViewArgument(argument);
        Task task = taskService.findById(taskViewArgument.getTaskId());
        return new StrategyResponse(createMessageFromEntity(task));
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.TASK_VIEW;
    }
}
