package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.event.TaskViewEvent;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.strategy.Strategy;

import java.util.List;

import static ru.homework.tasktracker.mapper.TaskEventMapper.toTaskViewEvent;
import static ru.homework.tasktracker.util.MessageHelperUtil.createMessageFromEntity;
import static ru.homework.tasktracker.util.MessageHelperUtil.createMessageFromListOfEntities;

@Component
@RequiredArgsConstructor
public class TaskViewStrategy implements Strategy {

    private final TaskService taskService;

    @Override
    public StrategyResponse execute(String argument) {
        StrategyResponse strategyResponse = new StrategyResponse();
        TaskViewEvent taskViewEvent = toTaskViewEvent(argument);
        Task task = taskService.findById(taskViewEvent.getTaskId());
        strategyResponse.setMessage(createMessageFromEntity(task));
        return strategyResponse;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.TASK_VIEW;
    }
}
