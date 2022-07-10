package ru.homework.tasktracker.subscriber.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.Event;
import ru.homework.tasktracker.model.Filter;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.entity.TaskStatus;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.subscriber.TaskSubscriber;

import java.util.List;
import java.util.stream.Collectors;

import static ru.homework.tasktracker.util.MessageHelper.createMessageFromEntity;
import static ru.homework.tasktracker.util.MessageHelper.createMessageFromListOfEntities;

@Component("task-view")
@RequiredArgsConstructor
@Slf4j
public class TaskViewSubscriber implements TaskSubscriber {
    private final static String STATUS_FILTER = "-fs";
    private final TaskService taskService;

    @Override
    public void execute(Event event) {
        String args = event.getArgs();
        if (args == null) {
            List<Task> tasks = taskService.findAll();
            log.info(createMessageFromListOfEntities(
                    "Список всех задач: ",
                    "Задач в бд вообще нет",
                    tasks));
        } else if (filterIsAbsent(args)) {
            Task task = taskService.findById(Long.valueOf(args));
            log.info(createMessageFromEntity(task));
        } else {
            viewWithFilter(args);
        }
    }

    private void viewWithFilter(String filterString) {
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
        log.info(createMessageFromListOfEntities(
                String.format("Список задач со статусом %s:", status),
                String.format("Список задач со статусом %s нет в бд", status),
                tasks));
    }

    private boolean filterIsAbsent(String arg) {
        return !arg.startsWith("-");
    }
}
