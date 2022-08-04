package ru.homework.tasktracker.model.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
public class TaskCreateDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private Long userId;
    @NotBlank
    private Long projectId;
    @NotBlank
    private LocalDate date;
}
