package ru.homework.tasktracker.mapper;

import ru.homework.tasktracker.model.dto.ProjectFullDto;
import ru.homework.tasktracker.model.dto.ProjectPostDto;
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
        projectFullDto.setUsers(project.getUsers().stream()
                .map(UserMapper::userToUserFullDto)
                .collect(Collectors.toList()));
        return projectFullDto;
    }

    public static Project projectPostDtoToProject(ProjectPostDto projectPostDto) {
        Project project = new Project();
        project.setTitle(projectPostDto.getTitle());
        project.setDescription(projectPostDto.getTitle());
        return project;
    }

    public static void projectPostDtoMergeWithProject(ProjectPostDto projectPostDto, Project project) {
        if (projectPostDto.getDescription() != null ) {
            project.setDescription(projectPostDto.getDescription());
        }
        if (projectPostDto.getTitle() != null) {
            project.setTitle(projectPostDto.getTitle());
        }
    }
}
