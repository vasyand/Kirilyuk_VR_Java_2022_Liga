package ru.homework.tasktracker.strategy.argument;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.homework.tasktracker.model.filter.TaskFilter;

@AllArgsConstructor
@Getter
public class TaskViewAllArgument {
    private TaskFilter taskFilter;
}
