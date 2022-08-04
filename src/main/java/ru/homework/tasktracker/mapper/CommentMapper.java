package ru.homework.tasktracker.mapper;

import org.mapstruct.*;
import ru.homework.tasktracker.model.dto.CommentCreateDto;
import ru.homework.tasktracker.model.dto.CommentFullDto;
import ru.homework.tasktracker.model.dto.CommentUpdateDto;
import ru.homework.tasktracker.model.entity.Comment;
import ru.homework.tasktracker.model.entity.Task;

import static org.mapstruct.MappingConstants.ComponentModel.*;
import static org.mapstruct.NullValuePropertyMappingStrategy.*;

@Mapper(componentModel = SPRING)
public interface CommentMapper {
    @Mapping(target = "taskId", source = "comment.task.id")
    CommentFullDto commentToCommentFullDto(Comment comment);
    @Mapping(target = "task", source = "commentCreateDto.taskId", qualifiedByName = "getTaskWithId")
    Comment commentCreateDtoToComment(CommentCreateDto commentCreateDto);

    @Mapping(target = "message", nullValuePropertyMappingStrategy = IGNORE)
    Comment commentUpdateDtoMergeWithComment(CommentUpdateDto commentUpdateDto, @MappingTarget Comment comment);

    @Named("getTaskWithId")
    static Task getTaskWithId(Long id) {
        Task task = new Task();
        task.setId(id);
        return task;
    }
}
