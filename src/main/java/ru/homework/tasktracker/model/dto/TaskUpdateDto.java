package ru.homework.tasktracker.model.dto;

import lombok.Getter;
import ru.homework.tasktracker.model.TaskStatus;

import java.time.LocalDate;

@Getter
public class TaskUpdateDto {
    private String title;
    private String description;
    private Long userId;
    private Long projectId;
    private LocalDate date;
    private TaskStatus taskStatus;
}
