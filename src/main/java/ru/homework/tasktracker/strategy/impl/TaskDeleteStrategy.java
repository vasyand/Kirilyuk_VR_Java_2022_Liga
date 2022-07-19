package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.TaskStrategyName;
import ru.homework.tasktracker.model.event.TaskDeleteEvent;
import ru.homework.tasktracker.model.event.TaskEvent;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.strategy.TaskStrategy;

import static ru.homework.tasktracker.mapper.TaskEventMapper.toTaskDeleteEvent;

@Component
@RequiredArgsConstructor
public class TaskDeleteStrategy implements TaskStrategy {
    private final TaskService taskService;

    @Override
    public StrategyResponse execute(TaskEvent event) {
        TaskDeleteEvent taskDeleteEvent = toTaskDeleteEvent(event);
        taskService.delete(taskDeleteEvent.getTaskId());
        return new StrategyResponse("Задача успешно удалена!");
    }

    @Override
    public TaskStrategyName getStrategyName() {
        return TaskStrategyName.DELETE;
    }
}
