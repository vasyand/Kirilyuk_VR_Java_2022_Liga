package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.UserStrategyName;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.model.event.UserEvent;
import ru.homework.tasktracker.model.event.UserViewWithMaxNumberTasksEvent;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.UserStrategy;

import static ru.homework.tasktracker.mapper.UserEventMapper.toUserViewWithMaxNumberTasksEvent;
import static ru.homework.tasktracker.util.MessageHelper.createMessageFromEntity;

@Component
@RequiredArgsConstructor
public class UserViewWithMaxNumberTasksStrategy implements UserStrategy {
    private final UserService userService;

    @Override
    public StrategyResponse execute(UserEvent taskEvent) {
        UserViewWithMaxNumberTasksEvent event = toUserViewWithMaxNumberTasksEvent(taskEvent);
        StrategyResponse strategyResponse = new StrategyResponse();
        User user = userService.findUserWithMaxNumberTasks(event.getUserFilter());
        strategyResponse.setMessage(createMessageFromEntity(user));
        return strategyResponse;
    }

    @Override
    public UserStrategyName getStrategyName() {
        return UserStrategyName.VIEWMT;
    }
}
