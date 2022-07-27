package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.UserViewWithMaxNumberTasksArgument;

import static ru.homework.tasktracker.strategy.mapper.UserStrategyArgumentMapper.toUserViewWithMaxNumberTasksArgument;
import static ru.homework.tasktracker.util.MessageHelperUtil.createMessageFromEntity;

@Component
@RequiredArgsConstructor
public class UserViewWithMaxNumberTasksStrategy implements Strategy {
    private final UserService userService;

    @Override
    public StrategyResponse execute(String argument) {
        UserViewWithMaxNumberTasksArgument event = toUserViewWithMaxNumberTasksArgument(argument);
        User user = userService.findUserWithMaxNumberTasks(event.getUserFilter());
        return new StrategyResponse(createMessageFromEntity(user));
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.USER_WITH_MAX_NUMBER_TASKS;
    }
}
