package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.Comment;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.service.CommentService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.CommentViewAllArgument;

import java.util.List;

import static ru.homework.tasktracker.model.StrategyName.COMMENT_VIEW_ALL;
import static ru.homework.tasktracker.strategy.mapper.CommentStrategyArgumentMapper.toCommentViewAllArgument;
import static ru.homework.tasktracker.util.MessageHelperUtil.createMessageFromListOfEntities;

@Component
@RequiredArgsConstructor
public class CommentViewAllStrategy implements Strategy {
    private final CommentService commentService;

    @Override
    public StrategyResponse execute(String argument) {
        CommentViewAllArgument commentViewAllArgument = toCommentViewAllArgument(argument);
        List<Comment> comments = commentService.findAll(commentViewAllArgument.getCommentFilter());
        return new StrategyResponse(createMessageFromListOfEntities(
                "Список комментариев: \n",
                "Комментариев в бд вообще нет",
                comments));
    }

    @Override
    public StrategyName getStrategyName() {
        return COMMENT_VIEW_ALL;
    }
}
