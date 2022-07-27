package ru.homework.tasktracker.strategy.argument;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.homework.tasktracker.model.filter.ProjectFilter;

@Getter
@AllArgsConstructor
public class ProjectViewAllArgument {
    private ProjectFilter projectFilter;
}
