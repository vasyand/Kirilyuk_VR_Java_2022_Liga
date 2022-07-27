package ru.homework.tasktracker.strategy.argument;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateArgument {
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String email;
    private final String password;
}
