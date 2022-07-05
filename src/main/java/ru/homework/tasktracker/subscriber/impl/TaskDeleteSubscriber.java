package ru.homework.tasktracker.subscriber.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.Event;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.subscriber.TaskSubscriber;

@Component("task-delete")
@RequiredArgsConstructor
public class TaskDeleteSubscriber implements TaskSubscriber {
    private final TaskService taskService;

    @Override
    public void execute(Event event) {
        String taskId = event.getArgs();
        if (taskId == null) {
            throw new RuntimeException("Для удаления задачи после команды надо ввести его id");
        }
        taskService.delete(Long.valueOf(taskId));
    }
}
