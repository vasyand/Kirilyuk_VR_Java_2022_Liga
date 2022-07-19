package ru.homework.tasktracker.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.homework.tasktracker.model.entity.TaskStatus;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TaskEditEvent {
    private final Long taskId;
    private final String title;
    private final String description;
    private final Long userId;
    private final LocalDate date;
    private final TaskStatus status;
}
