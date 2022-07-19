package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.mapper.UserEventMapper;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.UserStrategyName;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.model.event.UserEvent;
import ru.homework.tasktracker.model.event.UserViewTaskEvent;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.UserStrategy;

import static ru.homework.tasktracker.util.MessageHelper.createMessageFromListOfEntities;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserViewTaskStrategy implements UserStrategy {
    private final UserService userService;

    @Override
    public StrategyResponse execute(UserEvent event) {
        StrategyResponse strategyResponse = new StrategyResponse();
        UserViewTaskEvent userViewTaskEvent = UserEventMapper.toUserViewTaskEvent(event);
        User user = userService.findById(userViewTaskEvent.getId(), userViewTaskEvent.getUserFilter());
        strategyResponse.setMessage(createMessageFromListOfEntities(
                String.format("Список задач со статусом %s у пользователя %s: ",
                        userViewTaskEvent.getUserFilter().getTaskStatus(), user.getName()),
                String.format("Задач со статусом %s у пользователя %s сейчас нет ",
                        userViewTaskEvent.getUserFilter().getTaskStatus(), user.getName()),
                user.getTasks()));
        return strategyResponse;
    }

    @Override
    public UserStrategyName getStrategyName() {
        return UserStrategyName.VIEWT;
    }
}
