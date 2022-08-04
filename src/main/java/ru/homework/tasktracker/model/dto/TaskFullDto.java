package ru.homework.tasktracker.model.dto;

import lombok.Getter;
import lombok.Setter;
import ru.homework.tasktracker.model.TaskStatus;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
public class TaskFullDto {
    private Long id;
    private String title;
    private String description;
    private Long userId;
    private Long projectId;
    private List<CommentFullDto> comments;
    private LocalDate date;
    private TaskStatus taskStatus;
}
