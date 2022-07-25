package ru.homework.tasktracker.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateEvent {
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String email;
    private final String password;
}
