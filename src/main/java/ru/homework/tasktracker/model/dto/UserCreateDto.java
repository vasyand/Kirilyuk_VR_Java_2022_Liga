package ru.homework.tasktracker.model.dto;

import lombok.Getter;

@Getter
public class UserCreateDto {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
}
