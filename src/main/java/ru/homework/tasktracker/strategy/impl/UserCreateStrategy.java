package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.model.event.UserCreateEvent;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.Strategy;

import static ru.homework.tasktracker.mapper.UserEventMapper.toUserCreateEvent;

@Component
@RequiredArgsConstructor
public class UserCreateStrategy implements Strategy {
    private final UserService userService;

    @Override
    public StrategyResponse execute(String argument) {
        UserCreateEvent userCreateEvent = toUserCreateEvent(argument);
        userService.save(new User(userCreateEvent.getName()));
        return new StrategyResponse("Пользователь успешно сохранен!");
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.USER_CREATE;
    }
}
