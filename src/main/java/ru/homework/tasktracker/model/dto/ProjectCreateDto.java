package ru.homework.tasktracker.model.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ProjectCreateDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
