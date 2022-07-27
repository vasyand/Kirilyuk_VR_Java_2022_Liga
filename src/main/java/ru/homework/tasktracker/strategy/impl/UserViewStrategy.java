package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.UserViewArgument;

import static ru.homework.tasktracker.strategy.mapper.UserStrategyArgumentMapper.toUserViewArgument;
import static ru.homework.tasktracker.util.MessageHelperUtil.createMessageFromEntity;

@Component
@RequiredArgsConstructor
public class UserViewStrategy implements Strategy {
    private final UserService userService;

    @Override
    public StrategyResponse execute(String argument) {
        UserViewArgument userViewArgument = toUserViewArgument(argument);
        User user = userService.findById(userViewArgument.getUserId());
        return new StrategyResponse(createMessageFromEntity(user));
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.USER_VIEW;
    }
}
