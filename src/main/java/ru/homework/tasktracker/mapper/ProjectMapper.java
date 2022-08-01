package ru.homework.tasktracker.mapper;

import ru.homework.tasktracker.model.dto.ProjectFullDto;
import ru.homework.tasktracker.model.dto.ProjectCreateDto;
import ru.homework.tasktracker.model.dto.ProjectUpdateDto;
import ru.homework.tasktracker.model.entity.Project;

import java.util.stream.Collectors;

public class ProjectMapper {
    public static ProjectFullDto projectToProjectFullDto(Project project) {
        ProjectFullDto projectFullDto = new ProjectFullDto();
        projectFullDto.setId(project.getId());
        projectFullDto.setTitle(project.getTitle());
        projectFullDto.setDescription(projectFullDto.getDescription());
        projectFullDto.setTasks(project.getTasks().stream()
                .map(TaskMapper::taskToTaskFullDto)
                .collect(Collectors.toList()));
        return projectFullDto;
    }

    public static Project projectPostDtoToProject(ProjectCreateDto projectCreateDto) {
        Project project = new Project();
        project.setTitle(projectCreateDto.getTitle());
        project.setDescription(projectCreateDto.getTitle());
        return project;
    }

    public static void projectPostDtoMergeWithProject(ProjectUpdateDto projectUpdateDto, Project project) {
        if (projectUpdateDto.getDescription() != null ) {
            project.setDescription(projectUpdateDto.getDescription());
        }
        if (projectUpdateDto.getTitle() != null) {
            project.setTitle(projectUpdateDto.getTitle());
        }
    }
}
