package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.service.ProjectService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.ProjectCreateArgument;

import static ru.homework.tasktracker.model.StrategyName.PROJECT_CREATE;
import static ru.homework.tasktracker.strategy.mapper.ProjectStrategyArgumentMapper.toProjectCreateArgument;

@Component
@RequiredArgsConstructor
public class ProjectCreateStrategy implements Strategy {
    private final ProjectService projectService;

    @Override
    public StrategyResponse execute(String argument) {
        ProjectCreateArgument projectCreateArgument = toProjectCreateArgument(argument);
        projectService.save(new Project(projectCreateArgument.getTitle(), projectCreateArgument.getDescription()));
        return new StrategyResponse("Проект успешно сохранен!");
    }

    @Override
    public StrategyName getStrategyName() {
        return PROJECT_CREATE;
    }
}
