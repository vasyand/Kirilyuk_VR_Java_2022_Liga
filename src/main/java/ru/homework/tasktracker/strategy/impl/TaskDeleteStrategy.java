package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.event.TaskDeleteEvent;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.strategy.Strategy;

import static ru.homework.tasktracker.mapper.TaskEventMapper.toTaskDeleteEvent;

@Component
@RequiredArgsConstructor
public class TaskDeleteStrategy implements Strategy {
    private final TaskService taskService;

    @Override
    public StrategyResponse execute(String argument) {
        TaskDeleteEvent taskDeleteEvent = toTaskDeleteEvent(argument);
        taskService.delete(taskDeleteEvent.getTaskId());
        return new StrategyResponse("Задача успешно удалена!");
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.TASK_DELETE;
    }
}
