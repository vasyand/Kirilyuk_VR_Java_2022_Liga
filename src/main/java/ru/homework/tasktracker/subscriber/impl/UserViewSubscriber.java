package ru.homework.tasktracker.subscriber.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.Event;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.subscriber.UserSubscriber;

import java.util.List;

import static ru.homework.tasktracker.util.MessageHelper.createMessageFromEntity;
import static ru.homework.tasktracker.util.MessageHelper.createMessageFromListOfEntities;

@Component("user-view")
@RequiredArgsConstructor
@Slf4j
public class UserViewSubscriber implements UserSubscriber {
    private final UserService userService;

    @Override
    public void execute(Event event) {
        String userId = event.getArgs();
        if (userId == null) {
            List<User> users = userService.findAll();
            log.info(createMessageFromListOfEntities(
                    "Список всех пользователей: ",
                    "В бд вообще нет пользователей",
                    users));
        } else {
            User user = userService.findById(Long.valueOf(userId));
            log.info(createMessageFromEntity(user));
        }
    }
}
