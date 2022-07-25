package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.event.UserDeleteEvent;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.Strategy;

import static ru.homework.tasktracker.mapper.UserEventMapper.toUserDeleteEvent;

@Component
@RequiredArgsConstructor
public class UserDeleteStrategy implements Strategy {
    private final UserService userService;

    @Override
    public StrategyResponse execute(String argument) {
        UserDeleteEvent userDeleteEvent = toUserDeleteEvent(argument);
        userService.delete(userDeleteEvent.getUserId());
        return new StrategyResponse("Пользователь успешно удален!");
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.USER_DELETE;
    }
}
