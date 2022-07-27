package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.Comment;
import ru.homework.tasktracker.service.CommentService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.CommentViewArgument;

import static ru.homework.tasktracker.model.StrategyName.COMMENT_VIEW;
import static ru.homework.tasktracker.strategy.mapper.CommentStrategyArgumentMapper.toCommentViewArgument;
import static ru.homework.tasktracker.util.MessageHelperUtil.createMessageFromEntity;

@Component
@RequiredArgsConstructor
public class CommentViewStrategy implements Strategy {
    private final CommentService commentService;

    @Override
    public StrategyResponse execute(String argument) {
        CommentViewArgument commentViewArgument = toCommentViewArgument(argument);
        Comment comment = commentService.findById(commentViewArgument.getCommentId());
        return new StrategyResponse(createMessageFromEntity(comment));
    }

    @Override
    public StrategyName getStrategyName() {
        return COMMENT_VIEW;
    }
}
