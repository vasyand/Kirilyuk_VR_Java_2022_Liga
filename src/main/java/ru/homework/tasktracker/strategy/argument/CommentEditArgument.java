package ru.homework.tasktracker.strategy.argument;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class CommentEditArgument {
    private Long commentId;
    private String message;
    private Long taskId;
}
