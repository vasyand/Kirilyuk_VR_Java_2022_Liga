package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.service.CommentService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.CommentDeleteArgument;

import static ru.homework.tasktracker.model.StrategyName.COMMENT_DELETE;
import static ru.homework.tasktracker.strategy.mapper.CommentStrategyArgumentMapper.toCommentDeleteArgument;

@Component
@RequiredArgsConstructor
public class CommentDeleteStrategy implements Strategy {
    private final CommentService commentService;

    @Override
    public StrategyResponse execute(String argument) {
        CommentDeleteArgument commentDeleteArgument = toCommentDeleteArgument(argument);
        commentService.delete(commentDeleteArgument.getCommentId());
        return new StrategyResponse("Комментарий успешно удален!");
    }

    @Override
    public StrategyName getStrategyName() {
        return COMMENT_DELETE;
    }
}
