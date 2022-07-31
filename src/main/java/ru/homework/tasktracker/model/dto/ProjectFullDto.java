package ru.homework.tasktracker.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectFullDto {
    private Long id;
    private String title;
    private String description;
    private List<TaskFullDto> tasks;
    private List<UserFullDto> users;
}
