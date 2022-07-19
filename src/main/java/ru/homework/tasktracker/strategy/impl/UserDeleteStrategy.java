package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.UserStrategyName;
import ru.homework.tasktracker.model.event.UserDeleteEvent;
import ru.homework.tasktracker.model.event.UserEvent;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.UserStrategy;

import static ru.homework.tasktracker.mapper.UserEventMapper.toUserDeleteEvent;

@Component
@RequiredArgsConstructor
public class UserDeleteStrategy implements UserStrategy {
    private final UserService userService;

    @Override
    public StrategyResponse execute(UserEvent event) {
        UserDeleteEvent userDeleteEvent = toUserDeleteEvent(event);
        userService.delete(userDeleteEvent.getUserId());
        return new StrategyResponse("Пользователь успешно удален!");
    }

    @Override
    public UserStrategyName getStrategyName() {
        return UserStrategyName.DELETE;
    }
}
