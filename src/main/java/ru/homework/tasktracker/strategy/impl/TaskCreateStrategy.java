package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.TaskStrategyName;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.entity.TaskStatus;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.model.event.TaskCreateEvent;
import ru.homework.tasktracker.model.event.TaskEvent;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.TaskStrategy;

import static ru.homework.tasktracker.mapper.TaskEventMapper.toTaskCreateEvent;

@Component
@RequiredArgsConstructor
public class TaskCreateStrategy implements TaskStrategy {

    private final TaskService taskService;
    private final UserService userService;

    @Override
    public StrategyResponse execute(TaskEvent event) {
        TaskCreateEvent taskCreateEvent = toTaskCreateEvent(event);
        User userFoTask = userService.findById(taskCreateEvent.getUserId());
        taskService.save(new Task(taskCreateEvent.getTitle(),
                taskCreateEvent.getDescription(),
                userFoTask,
                taskCreateEvent.getDate(),
                TaskStatus.CREATED));
        return new StrategyResponse("Задача успешно сохранена!");
    }

    @Override
    public TaskStrategyName getStrategyName() {
        return TaskStrategyName.CREATE;
    }
}
