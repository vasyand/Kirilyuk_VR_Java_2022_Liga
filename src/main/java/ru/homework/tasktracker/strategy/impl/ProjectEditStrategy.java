package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.service.ProjectService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.ProjectEditArgument;

import static ru.homework.tasktracker.model.StrategyName.PROJECT_EDIT;
import static ru.homework.tasktracker.strategy.mapper.ProjectStrategyArgumentMapper.toProjectEditArgument;

@Component
@RequiredArgsConstructor
public class ProjectEditStrategy implements Strategy {
    private final ProjectService projectService;

    @Override
    public StrategyResponse execute(String argument) {
        ProjectEditArgument projectEditArgument = toProjectEditArgument(argument);
        Project updatingProject = projectService.findById(projectEditArgument.getProjectId());
        merge(updatingProject, projectEditArgument);
        projectService.update(updatingProject);
        return new StrategyResponse("Проект успешно обновлен!");
    }

    private void merge(Project project, ProjectEditArgument projectEditArgument) {
        if (projectEditArgument.getTitle() != null) {
            project.setTitle(projectEditArgument.getTitle());
        }
        if (projectEditArgument.getDescription() != null) {
            project.setDescription(project.getDescription());
        }
    }

    @Override
    public StrategyName getStrategyName() {
        return PROJECT_EDIT;
    }
}
