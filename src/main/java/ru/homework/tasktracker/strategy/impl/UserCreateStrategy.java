package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.UserStrategyName;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.model.event.UserCreateEvent;
import ru.homework.tasktracker.model.event.UserEvent;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.UserStrategy;

import static ru.homework.tasktracker.mapper.UserEventMapper.toUserCreateEvent;

@Component
@RequiredArgsConstructor
public class UserCreateStrategy implements UserStrategy {
    private final UserService userService;

    @Override
    public StrategyResponse execute(UserEvent event) {
        UserCreateEvent userCreateEvent = toUserCreateEvent(event);
        userService.save(new User(userCreateEvent.getName()));
        return new StrategyResponse("Пользователь успешно сохранен!");
    }

    @Override
    public UserStrategyName getStrategyName() {
        return UserStrategyName.CREATE;
    }
}
