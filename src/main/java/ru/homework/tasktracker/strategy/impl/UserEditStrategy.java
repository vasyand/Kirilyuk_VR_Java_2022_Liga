package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.strategy.argument.UserEditArgument;

import static ru.homework.tasktracker.strategy.mapper.UserStrategyArgumentMapper.toUserEditArgument;

@Component
@RequiredArgsConstructor
public class UserEditStrategy implements Strategy {

    private final UserService userService;

    @Override
    public StrategyResponse execute(String argument) {
        UserEditArgument userEditArgument = toUserEditArgument(argument);
        User updatingUser = userService.findById(userEditArgument.getUserId());
        merge(updatingUser, userEditArgument);
        userService.update(updatingUser);
        return new StrategyResponse("Пользователь успешно изменен!");
    }

    private void merge(User user, UserEditArgument userEditArgument) {
        if (userEditArgument.getFirstName() != null) {
            user.setFirstName(userEditArgument.getFirstName());
        }
        if (userEditArgument.getMiddleName() != null) {
            user.setMiddleName(userEditArgument.getMiddleName());
        }
        if (userEditArgument.getLastName() != null) {
            user.setLastName(userEditArgument.getLastName());
        }
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.USER_EDIT;
    }
}
