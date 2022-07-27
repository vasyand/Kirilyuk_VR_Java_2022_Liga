package ru.homework.tasktracker.strategy.argument;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectEditArgument {
    private Long projectId;
    private String title;
    private String description;
}
