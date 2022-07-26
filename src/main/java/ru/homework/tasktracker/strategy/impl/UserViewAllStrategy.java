package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.model.event.UserViewAllEvent;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.Strategy;

import java.util.List;

import static ru.homework.tasktracker.mapper.UserEventMapper.toUserViewAllEvent;
import static ru.homework.tasktracker.util.MessageHelperUtil.createMessageFromListOfEntities;

@Component
@RequiredArgsConstructor
public class UserViewAllStrategy implements Strategy {
    private final UserService userService;

    @Override
    public StrategyResponse execute(String argument) {
        StrategyResponse strategyResponse = new StrategyResponse();
        UserViewAllEvent userViewAllEvent = toUserViewAllEvent(argument);
        List<User> users = userService.findAll(userViewAllEvent.getUserFilter());
        strategyResponse.setMessage(createMessageFromListOfEntities(
                "Список пользователей: \n",
                "Пользователей нет",
                users));
        return strategyResponse;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.USER_VIEW_ALL;
    }
}
