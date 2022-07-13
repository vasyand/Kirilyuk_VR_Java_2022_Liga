package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.UserEvent;
import ru.homework.tasktracker.model.UserStrategyName;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.UserStrategy;

import static ru.homework.tasktracker.model.StrategyResponse.*;

@Component
@RequiredArgsConstructor
public class UserCreateStrategy implements UserStrategy {
    private static final int NUMBER_OF_USER_FIELDS = 1;
    private final UserService userService;

    @Override
    public StrategyResponse execute(UserEvent event) {
        try {
            String fields = event.getArgs();
            if (fields == null) {
                throw new RuntimeException("Для создания пользователя надо ввести его данные в виде: id,имя");
            }
            String[] userFields = fields.split(",");
            if (userFields.length != NUMBER_OF_USER_FIELDS) {
                throw new RuntimeException("Неверное количество полей для создания пользователя");
            }
            userService.save(new User(userFields[0]));
            return new StrategyResponse("Пользователь успешно сохранен!", Status.OK);
        } catch (RuntimeException e) {
            return new StrategyResponse(e.getMessage(), Status.BAD);
        }
    }

    @Override
    public UserStrategyName getStrategyName() {
        return UserStrategyName.CREATE;
    }
}
