package ru.homework.tasktracker.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UserUpdateDto {
    private String firstName;
    private String middleName;
    private String lastName;
}
