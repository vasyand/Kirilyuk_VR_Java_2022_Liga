package ru.homework.tasktracker.subscriber.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.Event;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.subscriber.UserSubscriber;

@Component("user-create")
@RequiredArgsConstructor
public class UserCreateSubscriber implements UserSubscriber {
    private static final int NUMBER_OF_USER_FIELDS = 1;
    private final UserService userService;

    @Override
    public void execute(Event event) {
        String fields = event.getArgs();
        if (fields == null) {
            throw new RuntimeException("Для создания пользователя надо ввести его данные в виде: id,имя");
        }
        String[] userFields = fields.split(",");
        if (userFields.length != NUMBER_OF_USER_FIELDS) {
            throw new RuntimeException("Неверное количество полей для создания пользователя");
        }
        userService.save(new User(userFields[0]));
    }
}
