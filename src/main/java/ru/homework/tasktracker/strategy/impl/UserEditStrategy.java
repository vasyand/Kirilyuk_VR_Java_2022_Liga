package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.UserStrategyName;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.model.event.UserEditEvent;
import ru.homework.tasktracker.model.event.UserEvent;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.UserStrategy;

import static ru.homework.tasktracker.mapper.UserEventMapper.toUserEditEvent;

@Component
@RequiredArgsConstructor
public class UserEditStrategy implements UserStrategy {

    private final UserService userService;

    @Override
    public StrategyResponse execute(UserEvent event) {
        UserEditEvent userEditEvent = toUserEditEvent(event);
        User updatingUser = userService.findById(userEditEvent.getUserId());
        if (userEditEvent.getName() != null) {
            updatingUser.setName(userEditEvent.getName());
        }
        userService.update(updatingUser);
        return new StrategyResponse("Пользователь успешно изменен!");
    }

    @Override
    public UserStrategyName getStrategyName() {
        return UserStrategyName.EDIT;
    }
}
