package ru.homework.tasktracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ru.homework.tasktracker.model.dto.TaskCreateDto;
import ru.homework.tasktracker.model.dto.TaskFullDto;
import ru.homework.tasktracker.model.dto.TaskUpdateDto;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.entity.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING, uses = CommentMapper.class)
public interface TaskMapper {
    @Mapping(target = "userId", source = "task.user.id")
    @Mapping(target = "projectId", source = "task.project.id")
    TaskFullDto taskToTaskFullDto(Task task);

    @Mapping(target = "project", source = "projectId", qualifiedByName = "getProjectWithId")
    @Mapping(target = "user", source = "userId", qualifiedByName = "getUserWithId")
    Task taskCreateDtoToTask(TaskCreateDto taskCreateDto);

    @Mapping(target = "project", source = "projectId",
            qualifiedByName = "getProjectWithId", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "user", source = "userId",
            qualifiedByName = "getUserWithId", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "title", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "description", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "date", nullValuePropertyMappingStrategy = IGNORE)
    Task taskUpdateDtoMergeWithTask(TaskUpdateDto taskUpdateDto, @MappingTarget Task task);

    @Named("getProjectWithId")
    static Project getProjectWithId(Long id) {
        Project project = new Project();
        project.setId(id);
        return project;
    }

    @Named("getUserWithId")
    static User getUserWithId(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }


}
