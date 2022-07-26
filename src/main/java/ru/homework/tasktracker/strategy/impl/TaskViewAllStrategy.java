package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.event.TaskViewAllEvent;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.strategy.Strategy;

import java.util.List;

import static ru.homework.tasktracker.mapper.TaskEventMapper.toTaskViewAllEvent;
import static ru.homework.tasktracker.util.MessageHelperUtil.createMessageFromListOfEntities;

@Component
@RequiredArgsConstructor
public class TaskViewAllStrategy implements Strategy {
    private final TaskService taskService;

    @Override
    public StrategyResponse execute(String argument) {
        StrategyResponse strategyResponse = new StrategyResponse();
        TaskViewAllEvent taskViewAllEvent = toTaskViewAllEvent(argument);
        List<Task> tasks = taskService.findAll(taskViewAllEvent.getTaskFilter());
        strategyResponse.setMessage(createMessageFromListOfEntities(
                "Список всех задач: \n",
                "Задач в бд вообще нет",
                tasks));
        return strategyResponse;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.TASK_VIEW_ALL;
    }
}
