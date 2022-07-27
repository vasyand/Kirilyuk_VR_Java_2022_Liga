package ru.homework.tasktracker.strategy.argument;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.homework.tasktracker.model.filter.CommentFilter;

@AllArgsConstructor
@Getter
public class CommentViewAllArgument {
    private CommentFilter commentFilter;
}
