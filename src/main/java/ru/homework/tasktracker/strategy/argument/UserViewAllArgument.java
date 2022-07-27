package ru.homework.tasktracker.strategy.argument;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.homework.tasktracker.model.filter.UserFilter;

@Getter
@AllArgsConstructor
public class UserViewAllArgument {
    private final UserFilter userFilter;
}
