package ru.homework.tasktracker.mapper;

import ru.homework.tasktracker.model.dto.CommentCreateDto;
import ru.homework.tasktracker.model.dto.CommentFullDto;
import ru.homework.tasktracker.model.dto.CommentUpdateDto;
import ru.homework.tasktracker.model.entity.Comment;
import ru.homework.tasktracker.model.entity.Task;

public class CommentMapper {
    public static CommentFullDto commentToCommentFullDto(Comment comment) {
        CommentFullDto commentFullDto = new CommentFullDto();
        commentFullDto.setId(comment.getId());
        commentFullDto.setMessage(comment.getMessage());
        commentFullDto.setTaskId(comment.getTask().getId());
        return commentFullDto;
    }

    public static Comment commentCreateDtoToComment(CommentCreateDto commentCreateDto) {
        Comment comment = new Comment();
        Task task = new Task();
        task.setId(commentCreateDto.getTaskId());
        comment.setTask(task);
        comment.setMessage(commentCreateDto.getMessage());
        return comment;
    }

    public static void commentUpdateDtoMergeWithComment(CommentUpdateDto commentUpdateDto, Comment comment) {
        if (commentUpdateDto.getMessage() != null) {
            comment.setMessage(commentUpdateDto.getMessage());
        }
    }
}
