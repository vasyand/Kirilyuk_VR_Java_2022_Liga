package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.Comment;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.service.CommentService;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.CommentEditArgument;

import static ru.homework.tasktracker.model.StrategyName.COMMENT_EDIT;
import static ru.homework.tasktracker.strategy.mapper.CommentStrategyArgumentMapper.toCommentEditArgument;

@Component
@RequiredArgsConstructor
public class CommentEditStrategy implements Strategy {
    private final CommentService commentService;
    private final TaskService taskService;

    @Override
    public StrategyResponse execute(String argument) {
        CommentEditArgument commentEditArgument = toCommentEditArgument(argument);
        Comment updatedComment = commentService.findById(commentEditArgument.getCommentId());
        merge(updatedComment, commentEditArgument);
        commentService.update(updatedComment);
        return new StrategyResponse("Комментарий успешно обновлен!");
    }

    private void merge(Comment comment, CommentEditArgument commentEditArgument) {
        if (commentEditArgument.getMessage() != null) {
            comment.setMessage(commentEditArgument.getMessage());
        }
        if (commentEditArgument.getTaskId() != null) {
            Task task = taskService.findById(commentEditArgument.getCommentId());
            comment.setTask(task);
        }
    }

    @Override
    public StrategyName getStrategyName() {
        return COMMENT_EDIT;
    }
}
