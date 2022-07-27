package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.UserViewAllArgument;

import java.util.List;

import static ru.homework.tasktracker.strategy.mapper.UserStrategyArgumentMapper.toUserViewAllArgument;
import static ru.homework.tasktracker.util.MessageHelperUtil.createMessageFromListOfEntities;

@Component
@RequiredArgsConstructor
public class UserViewAllStrategy implements Strategy {
    private final UserService userService;

    @Override
    public StrategyResponse execute(String argument) {
        UserViewAllArgument userViewAllArgument = toUserViewAllArgument(argument);
        List<User> users = userService.findAll(userViewAllArgument.getUserFilter());
        return new StrategyResponse(createMessageFromListOfEntities(
                "Список пользователей: \n",
                "Пользователей нет",
                users));
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.USER_VIEW_ALL;
    }
}
