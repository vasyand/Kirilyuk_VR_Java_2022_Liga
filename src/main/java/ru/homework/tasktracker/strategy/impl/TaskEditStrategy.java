package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.model.event.TaskEditEvent;
import ru.homework.tasktracker.service.ProjectService;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.Strategy;

import static ru.homework.tasktracker.mapper.TaskEventMapper.toTaskEditEvent;

@Component
@RequiredArgsConstructor
public class TaskEditStrategy implements Strategy {
    private final TaskService taskService;
    private final UserService userService;
    private final ProjectService projectService;

    @Override
    public StrategyResponse execute(String argument) {
        TaskEditEvent taskEditEvent = toTaskEditEvent(argument);
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
            User userForTask = userService.findById(taskEditEvent.getUserId());
            task.setUser(userForTask);
        }
        if (taskEditEvent.getProjectId() != null) {
            Project project = projectService.findById(taskEditEvent.getProjectId());
            task.setProject(project);
        }
        if (taskEditEvent.getDate() != null) {
            task.setDate(taskEditEvent.getDate());
        }
        if (taskEditEvent.getStatus() != null) {
            task.setTaskStatus(taskEditEvent.getStatus());
        }
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.TASK_EDIT;
    }
}
