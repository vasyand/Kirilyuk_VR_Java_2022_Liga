package ru.homework.tasktracker.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.homework.tasktracker.model.filter.UserFilter;

@Getter
@AllArgsConstructor
public class UserViewEvent {
    private final Long userId;
}
