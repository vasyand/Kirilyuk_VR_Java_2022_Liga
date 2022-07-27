package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.service.ProjectService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.ProjectViewArgument;

import static ru.homework.tasktracker.model.StrategyName.PROJECT_VIEW;
import static ru.homework.tasktracker.strategy.mapper.ProjectStrategyArgumentMapper.toProjectViewArgument;
import static ru.homework.tasktracker.util.MessageHelperUtil.createMessageFromEntity;

@Component
@RequiredArgsConstructor
public class ProjectViewStrategy implements Strategy {
    private final ProjectService projectService;

    @Override
    public StrategyResponse execute(String argument) {
        ProjectViewArgument projectViewArgument = toProjectViewArgument(argument);
        Project project = projectService.findById(projectViewArgument.getProjectId());
        return new StrategyResponse(createMessageFromEntity(project));
    }

    @Override
    public StrategyName getStrategyName() {
        return PROJECT_VIEW;
    }
}
