package ru.homework.tasktracker.model.dto;

import lombok.Getter;
import lombok.Setter;
import ru.homework.tasktracker.model.entity.Role;

import java.util.List;

@Setter
@Getter
public class UserFullDto {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private List<TaskFullDto> tasks;
    private List<ProjectFullDto> projects;
}
