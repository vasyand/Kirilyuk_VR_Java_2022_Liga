package ru.homework.tasktracker.strategy.argument;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.homework.tasktracker.model.entity.TaskStatus;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TaskEditArgument {
    private final Long taskId;
    private final String title;
    private final String description;
    private final Long userId;
    private final Long projectId;
    private final LocalDate date;
    private final TaskStatus status;
}
