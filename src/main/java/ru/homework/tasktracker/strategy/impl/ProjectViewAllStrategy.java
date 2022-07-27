package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.service.ProjectService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.ProjectViewAllArgument;

import java.util.List;

import static ru.homework.tasktracker.model.StrategyName.PROJECT_VIEW_ALL;
import static ru.homework.tasktracker.strategy.mapper.ProjectStrategyArgumentMapper.toProjectViewAllArgument;
import static ru.homework.tasktracker.util.MessageHelperUtil.createMessageFromListOfEntities;

@Component
@RequiredArgsConstructor
public class ProjectViewAllStrategy implements Strategy {
    private final ProjectService projectService;

    @Override
    public StrategyResponse execute(String argument) {
        ProjectViewAllArgument projectViewAllArgument = toProjectViewAllArgument(argument);
        List<Project> projects = projectService.findAll(projectViewAllArgument.getProjectFilter());
        return new StrategyResponse(createMessageFromListOfEntities(
                "Список проектов: \n",
                "Проектов в бд вообще нет",
                projects));
    }

    @Override
    public StrategyName getStrategyName() {
        return PROJECT_VIEW_ALL;
    }
}
