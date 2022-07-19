package ru.homework.tasktracker.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserViewEvent {
    private final Long userId;
}
