package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.entity.TaskStatus;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.model.event.TaskCreateEvent;
import ru.homework.tasktracker.service.ProjectService;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.Strategy;

import static ru.homework.tasktracker.mapper.TaskEventMapper.toTaskCreateEvent;

@Component
@RequiredArgsConstructor
public class TaskCreateStrategy implements Strategy {

    private final TaskService taskService;
    private final UserService userService;
    private final ProjectService projectService;

    @Override
    public StrategyResponse execute(String argument) {
        TaskCreateEvent taskCreateEvent = toTaskCreateEvent(argument);
        User user = userService.findById(taskCreateEvent.getUserId());
        Project project = projectService.findById(taskCreateEvent.getProjectId());
        taskService.save(new Task(taskCreateEvent.getTitle(),
                taskCreateEvent.getDescription(),
                user,
                project,
                taskCreateEvent.getDate(),
                TaskStatus.CREATED));
        return new StrategyResponse("Задача успешно сохранена!");
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.TASK_CREATE;
    }
}
