package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.TaskStrategyName;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.event.TaskEvent;
import ru.homework.tasktracker.model.event.TaskViewEvent;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.strategy.TaskStrategy;

import java.util.List;

import static ru.homework.tasktracker.mapper.TaskEventMapper.toTaskViewEvent;
import static ru.homework.tasktracker.util.MessageHelper.createMessageFromEntity;
import static ru.homework.tasktracker.util.MessageHelper.createMessageFromListOfEntities;

@Component
@RequiredArgsConstructor
public class TaskViewStrategy implements TaskStrategy {

    private final TaskService taskService;

    @Override
    public StrategyResponse execute(TaskEvent event) {
        StrategyResponse strategyResponse = new StrategyResponse();
        TaskViewEvent taskViewEvent = toTaskViewEvent(event);
        if (taskViewEvent.getTaskId() == null) {
            List<Task> tasks = taskService.findAll();
            strategyResponse.setMessage(createMessageFromListOfEntities(
                    "Список всех задач: \n",
                    "Задач в бд вообще нет",
                    tasks));
        }
        else {
            Task task = taskService.findById(taskViewEvent.getTaskId());
            strategyResponse.setMessage(createMessageFromEntity(task));
        }
        return strategyResponse;
    }

    @Override
    public TaskStrategyName getStrategyName() {
        return TaskStrategyName.VIEW;
    }
}
