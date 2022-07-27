package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.*;
import ru.homework.tasktracker.service.CommentService;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.CommentCreateArgument;

import static ru.homework.tasktracker.model.StrategyName.*;
import static ru.homework.tasktracker.strategy.mapper.CommentStrategyArgumentMapper.toCommentCreateArgument;

@RequiredArgsConstructor
@Component
public class CommentCreateStrategy implements Strategy {
    private final CommentService commentService;
    private final TaskService taskService;

    @Override
    public StrategyResponse execute(String argument) {
        CommentCreateArgument commentCreateArgument = toCommentCreateArgument(argument);
        Task task = taskService.findById(commentCreateArgument.getTaskId());
        commentService.save(new Comment(commentCreateArgument.getMessage(), task));
        return new StrategyResponse("Комментарий успешно сохранен!");
    }

    @Override
    public StrategyName getStrategyName() {
        return COMMENT_CREATE;
    }
}
