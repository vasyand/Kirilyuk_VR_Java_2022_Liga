package ru.homework.tasktracker.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.homework.tasktracker.model.filter.TaskFilter;

@AllArgsConstructor
@Getter
public class TaskViewAllEvent {
    private TaskFilter taskFilter;
}
