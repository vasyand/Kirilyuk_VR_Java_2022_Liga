package ru.homework.tasktracker.strategy.argument;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TaskCreateArgument {
    private final String title;
    private final String description;
    private final Long userId;
    private final Long projectId;
    private final LocalDate date;
}
