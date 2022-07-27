package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.ProjectService;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.UserAddProjectArgument;

import static ru.homework.tasktracker.model.StrategyName.USER_ADD_PROJECT;
import static ru.homework.tasktracker.strategy.mapper.UserStrategyArgumentMapper.toAddProjectArgument;

@Component
@RequiredArgsConstructor
public class UserAddProjectStrategy implements Strategy {
    private final UserService userService;
    private final ProjectService projectService;

    @Override
    public StrategyResponse execute(String argument) {
        UserAddProjectArgument userAddProjectArgument = toAddProjectArgument(argument);
        User user = userService.findById(userAddProjectArgument.getUserId());
        Project project = projectService.findById(userAddProjectArgument.getProjectId());
        user.getProjects().add(project);
        project.getUsers().add(user);
        userService.update(user);
        projectService.update(project);
        return new StrategyResponse("Проект успешно добавлен пользователю");
    }

    @Override
    public StrategyName getStrategyName() {
        return USER_ADD_PROJECT;
    }
}
