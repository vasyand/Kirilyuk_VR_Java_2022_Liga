package ru.homework.tasktracker.model.dto;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
public class CommentCreateDto {
    @NotBlank
    private String message;
    @NotBlank
    @Min(1)
    private Long taskId;
}
