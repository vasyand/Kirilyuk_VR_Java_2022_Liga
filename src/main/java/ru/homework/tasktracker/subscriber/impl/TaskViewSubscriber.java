package ru.homework.tasktracker.subscriber.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.Event;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.subscriber.TaskSubscriber;

import java.util.List;
import java.util.stream.Collectors;

@Component("task-view")
@RequiredArgsConstructor
public class TaskViewSubscriber implements TaskSubscriber {
    private final TaskService taskService;

    @Override
    public void execute(Event event) {
        String taskId = event.getArgs();
        if (taskId == null) {
            System.out.println("Список всех задач: ");
            List<Task> tasks = taskService.findAll();
            tasks.forEach(System.out::println);
        } else if (!taskId.startsWith("-")) {
            Task task = taskService.findById(Long.valueOf(taskId));
            System.out.println(task);
        } else {
            viewWithFilter(event.getArgs());
        }
    }

    private void viewWithFilter(String command) {
        String[] filterWithArg = command.split("=");
        String filter = filterWithArg[0];
        if (!filter.equals("-fs") || filterWithArg.length == 1) {
           throw new RuntimeException(String.format("Фильтра %s не существует. Есть пока только фильтр \"-fs\"\n", filter));
        }
        String status = filterWithArg[1].trim();
        List<Task> tasks = taskService.findAll().stream()
                .filter(task -> task.getTaskStatus().toString().equals(status))
                .collect(Collectors.toList());
        if (tasks.isEmpty()) {
            System.out.format("Задач со статусом %s сейчас нет\n", status);
        } else {
            System.out.format("Список задач со статусом %s :\n", status);
            tasks.forEach(System.out::println);
        }

    }
}
