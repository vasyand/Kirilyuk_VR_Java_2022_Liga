package ru.homework.tasktracker.model.filter;

import lombok.Getter;
import ru.homework.tasktracker.model.TaskStatus;

import java.time.LocalDate;

@Getter
public class TaskFilter {
    private String title;
    private String description;
    private String userId;
    private String projectId;
    private TaskStatus taskStatus;
    private LocalDate to;
    private LocalDate from;
}
