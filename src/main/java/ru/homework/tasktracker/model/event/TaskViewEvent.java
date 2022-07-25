package ru.homework.tasktracker.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskViewEvent {
    private final Long taskId;
}
