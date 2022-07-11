package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.Filter;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.TaskEvent;
import ru.homework.tasktracker.model.TaskStrategyName;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.entity.TaskStatus;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.strategy.TaskStrategy;

import java.util.List;
import java.util.stream.Collectors;

import static ru.homework.tasktracker.model.StrategyResponse.*;
import static ru.homework.tasktracker.util.MessageHelper.createMessageFromEntity;
import static ru.homework.tasktracker.util.MessageHelper.createMessageFromListOfEntities;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskViewStrategy implements TaskStrategy {
    private final static String STATUS_FILTER = "-fs";
    private final TaskService taskService;

    @Override
    public StrategyResponse execute(TaskEvent event) {
        StrategyResponse strategyResponse = new StrategyResponse(Status.OK);
        try {
            String args = event.getArgs();
            if (args == null) {
                List<Task> tasks = taskService.findAll();
                strategyResponse.setMessage(createMessageFromListOfEntities(
                        "Список всех задач: \n",
                        "Задач в бд вообще нет",
                        tasks));
            } else if (filterIsAbsent(args)) {
                Task task = taskService.findById(Long.valueOf(args));
                strategyResponse.setMessage(createMessageFromEntity(task));
            } else {
                viewWithFilter(args, strategyResponse);
            }
            return strategyResponse;
        } catch (Exception e) {
            return new StrategyResponse(e.getMessage(), Status.BAD);
        }
    }

    private void viewWithFilter(String filterString, StrategyResponse strategyResponse) {
        Filter filter = new Filter(filterString);
        if (!filter.getFilter().equals(STATUS_FILTER)) {
            throw new RuntimeException(
                    String.format("Фильтра %s не существует. " +
                                    "Есть пока только фильтр \"-fs=status(CREATED,RUN,COMPLETED)\"",
                            filter.getFilter()));
        }
        if (filter.getArgument() == null) {
            throw new RuntimeException("Отсутствует аргумент для фильтра!");
        }
        String status = filter.getArgument();
        if (!TaskStatus.getStatusSet().contains(status)) {
            throw new RuntimeException(String.format("Статуса %s не существует!", status));
        }
        List<Task> tasks = taskService.findAll().stream()
                .filter(task -> task.getTaskStatus().toString().equals(status))
                .collect(Collectors.toList());
        strategyResponse.setMessage(createMessageFromListOfEntities(
                String.format("Список задач со статусом %s:", status),
                String.format("Список задач со статусом %s нет в бд", status),
                tasks));
    }

    private boolean filterIsAbsent(String arg) {
        return !arg.startsWith("-");
    }

    @Override
    public TaskStrategyName getStrategyName() {
        return TaskStrategyName.VIEW;
    }
}
