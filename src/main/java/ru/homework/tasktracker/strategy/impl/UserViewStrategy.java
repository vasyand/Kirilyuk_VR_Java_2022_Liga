package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.UserEvent;
import ru.homework.tasktracker.model.UserStrategyName;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.UserStrategy;

import java.util.List;

import static ru.homework.tasktracker.model.StrategyResponse.Status;
import static ru.homework.tasktracker.util.MessageHelper.createMessageFromEntity;
import static ru.homework.tasktracker.util.MessageHelper.createMessageFromListOfEntities;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserViewStrategy implements UserStrategy {
    private final UserService userService;

    @Override
    public StrategyResponse execute(UserEvent event) {
        String userId = event.getArgs();
        StrategyResponse strategyResponse = new StrategyResponse(Status.OK);
        try {
            if (userId == null) {
                List<User> users = userService.findAll();
                strategyResponse.setMessage(createMessageFromListOfEntities(
                        "Список всех пользователей: \n",
                        "В бд вообще нет пользователей",
                        users));
            } else {
                User user = userService.findById(Long.valueOf(userId));
                strategyResponse.setMessage(createMessageFromEntity(user));
            }
            return strategyResponse;
        } catch (Exception e) {
            return new StrategyResponse(e.getMessage(), Status.BAD);
        }
    }

    @Override
    public UserStrategyName getStrategyName() {
        return UserStrategyName.VIEW;
    }
}
