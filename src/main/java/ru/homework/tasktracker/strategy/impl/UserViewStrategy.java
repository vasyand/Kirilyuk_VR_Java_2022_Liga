package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.model.event.UserViewEvent;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.Strategy;

import static ru.homework.tasktracker.mapper.UserEventMapper.toUserViewEvent;
import static ru.homework.tasktracker.util.MessageHelperUtil.createMessageFromEntity;

@Component
@RequiredArgsConstructor
public class UserViewStrategy implements Strategy {
    private final UserService userService;

    @Override
    public StrategyResponse execute(String argument) {
        StrategyResponse strategyResponse = new StrategyResponse();
        UserViewEvent userViewEvent = toUserViewEvent(argument);
        User user = userService.findById(userViewEvent.getUserId());
        strategyResponse.setMessage(createMessageFromEntity(user));
        return strategyResponse;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.USER_VIEW;
    }
}
