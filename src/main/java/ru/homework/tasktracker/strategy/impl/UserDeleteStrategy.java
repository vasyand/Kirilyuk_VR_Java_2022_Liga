package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.UserDeleteArgument;

import static ru.homework.tasktracker.strategy.mapper.UserStrategyArgumentMapper.toUserDeleteArgument;

@Component
@RequiredArgsConstructor
public class UserDeleteStrategy implements Strategy {
    private final UserService userService;

    @Override
    public StrategyResponse execute(String argument) {
        UserDeleteArgument userDeleteArgument = toUserDeleteArgument(argument);
        userService.delete(userDeleteArgument.getUserId());
        return new StrategyResponse("Пользователь успешно удален!");
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.USER_DELETE;
    }
}
