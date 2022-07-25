package ru.homework.tasktracker.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserEditEvent {
    private final Long userId;
    private final String name;
}
