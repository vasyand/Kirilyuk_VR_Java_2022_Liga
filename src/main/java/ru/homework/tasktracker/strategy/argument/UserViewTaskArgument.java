package ru.homework.tasktracker.strategy.argument;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.homework.tasktracker.model.filter.UserFilter;

@Getter
@AllArgsConstructor
public class UserViewTaskArgument {
    private Long id;
    private UserFilter userFilter;
}
