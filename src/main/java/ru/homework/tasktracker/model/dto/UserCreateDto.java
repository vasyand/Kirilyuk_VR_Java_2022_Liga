package ru.homework.tasktracker.model.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserCreateDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String middleName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
