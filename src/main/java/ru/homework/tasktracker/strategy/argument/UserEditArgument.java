package ru.homework.tasktracker.strategy.argument;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserEditArgument {
    private final Long userId;
    private final String firstName;
    private final String middleName;
    private final String lastName;
}
