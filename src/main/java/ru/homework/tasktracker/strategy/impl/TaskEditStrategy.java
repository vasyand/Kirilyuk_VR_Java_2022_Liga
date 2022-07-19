package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.TaskStrategyName;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.model.event.TaskEditEvent;
import ru.homework.tasktracker.model.event.TaskEvent;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.TaskStrategy;

import static ru.homework.tasktracker.mapper.TaskEventMapper.toTaskEditEvent;

@Component
@RequiredArgsConstructor
public class TaskEditStrategy implements TaskStrategy {
    private final TaskService taskService;
    private final UserService userService;


    @Override
    public StrategyResponse execute(TaskEvent event) {
        TaskEditEvent taskEditEvent = toTaskEditEvent(event);
        Task updatedTask = taskService.findById(taskEditEvent.getTaskId());
        merge(updatedTask, taskEditEvent);
        taskService.update(updatedTask);
        return new StrategyResponse("Задача успешно обновлена!");
    }

    private void merge(Task task, TaskEditEvent taskEditEvent) {
        if (taskEditEvent.getTitle() != null) {
            task.setTitle(task.getTitle());
        }
        if (taskEditEvent.getDescription() != null) {
            task.setDescription(task.getDescription());
        }
        if (taskEditEvent.getUserId() != null) {
            User userForTask = userService.findById(taskEditEvent.getTaskId());
            task.setUser(userForTask);
        }
        if (taskEditEvent.getDate() != null) {
            task.setDate(taskEditEvent.getDate());
        }
        if (taskEditEvent.getStatus() != null) {
            task.setTaskStatus(taskEditEvent.getStatus());
        }
    }

    @Override
    public TaskStrategyName getStrategyName() {
        return TaskStrategyName.EDIT;
    }
}
