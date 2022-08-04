package ru.homework.tasktracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.homework.tasktracker.model.dto.ProjectCreateDto;
import ru.homework.tasktracker.model.dto.ProjectFullDto;
import ru.homework.tasktracker.model.dto.ProjectUpdateDto;
import ru.homework.tasktracker.model.entity.Project;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING, uses = TaskMapper.class)
public interface ProjectMapper {
    ProjectFullDto projectToProjectFullDto(Project project);

    Project projectPostDtoToProject(ProjectCreateDto projectCreateDto);

    @Mapping(target = "description", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "title", nullValuePropertyMappingStrategy = IGNORE)
    Project projectUpdateDtoMergeWithProject(ProjectUpdateDto projectUpdateDto, @MappingTarget Project project);
}
