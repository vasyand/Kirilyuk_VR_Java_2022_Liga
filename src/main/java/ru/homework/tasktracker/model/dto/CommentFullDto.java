package ru.homework.tasktracker.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentFullDto {
    private Long id;
    private String message;
    private Long taskId;
}
