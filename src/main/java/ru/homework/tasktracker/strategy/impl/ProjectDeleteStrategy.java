package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.service.ProjectService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.ProjectDeleteArgument;

import static ru.homework.tasktracker.model.StrategyName.PROJECT_DELETE;
import static ru.homework.tasktracker.strategy.mapper.ProjectStrategyArgumentMapper.toProjectDeleteArgument;

@Component
@RequiredArgsConstructor
public class ProjectDeleteStrategy implements Strategy {
    private final ProjectService projectService;

    @Override
    public StrategyResponse execute(String argument) {
        ProjectDeleteArgument projectDeleteArgument = toProjectDeleteArgument(argument);
        projectService.delete(projectDeleteArgument.getProjectId());
        return new StrategyResponse("Проект успешно удален!");
    }

    @Override
    public StrategyName getStrategyName() {
        return PROJECT_DELETE;
    }
}
