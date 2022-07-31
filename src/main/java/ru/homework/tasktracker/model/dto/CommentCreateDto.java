package ru.homework.tasktracker.model.dto;

import lombok.Getter;

@Getter
public class CommentCreateDto {
    private String message;
    private Long taskId;
}
