package ru.homework.tasktracker.strategy.argument;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDeleteArgument {
    private final Long commentId;
}
