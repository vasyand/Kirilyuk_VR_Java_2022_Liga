package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.ProjectService;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.TaskEditArgument;

import static ru.homework.tasktracker.strategy.mapper.TaskStrategyArgumentMapper.toTaskEditArgument;

@Component
@RequiredArgsConstructor
public class TaskEditStrategy implements Strategy {
    private final TaskService taskService;
    private final UserService userService;
    private final ProjectService projectService;

    @Override
    public StrategyResponse execute(String argument) {
        TaskEditArgument taskEditArgument = toTaskEditArgument(argument);
        Task updatingTask = taskService.findById(taskEditArgument.getTaskId());
        merge(updatingTask, taskEditArgument);
        taskService.update(updatingTask);
        return new StrategyResponse("Задача успешно обновлена!");
    }

    private void merge(Task task, TaskEditArgument taskEditArgument) {
        if (taskEditArgument.getTitle() != null) {
            task.setTitle(taskEditArgument.getTitle());
        }
        if (taskEditArgument.getDescription() != null) {
            task.setDescription(taskEditArgument.getDescription());
        }
        if (taskEditArgument.getUserId() != null) {
            User userForTask = userService.findById(taskEditArgument.getUserId());
            task.setUser(userForTask);
        }
        if (taskEditArgument.getProjectId() != null) {
            Project project = projectService.findById(taskEditArgument.getProjectId());
            task.setProject(project);
        }
        if (taskEditArgument.getDate() != null) {
            task.setDate(taskEditArgument.getDate());
        }
        if (taskEditArgument.getStatus() != null) {
            task.setTaskStatus(taskEditArgument.getStatus());
        }
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.TASK_EDIT;
    }
}
