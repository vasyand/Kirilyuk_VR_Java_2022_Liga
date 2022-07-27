package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.entity.TaskStatus;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.ProjectService;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.TaskCreateArgument;

import static ru.homework.tasktracker.strategy.mapper.TaskStrategyArgumentMapper.toTaskCreateArgument;

@Component
@RequiredArgsConstructor
public class TaskCreateStrategy implements Strategy {

    private final TaskService taskService;
    private final UserService userService;
    private final ProjectService projectService;

    @Override
    public StrategyResponse execute(String argument) {
        TaskCreateArgument taskCreateArgument = toTaskCreateArgument(argument);
        User user = userService.findById(taskCreateArgument.getUserId());
        Project project = projectService.findById(taskCreateArgument.getProjectId());
        taskService.save(new Task(taskCreateArgument.getTitle(),
                taskCreateArgument.getDescription(),
                user,
                project,
                taskCreateArgument.getDate(),
                TaskStatus.CREATED));
        return new StrategyResponse("Задача успешно сохранена!");
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.TASK_CREATE;
    }
}
