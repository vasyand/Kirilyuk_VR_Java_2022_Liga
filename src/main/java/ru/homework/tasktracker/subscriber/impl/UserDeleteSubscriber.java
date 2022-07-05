package ru.homework.tasktracker.subscriber.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.Event;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.subscriber.UserSubscriber;

@Component("user-delete")
@RequiredArgsConstructor
public class UserDeleteSubscriber implements UserSubscriber {
    private final UserService userService;

    @Override
    public void execute(Event event) {
        String userId = event.getArgs();
        if (userId == null) {
            throw new RuntimeException("Для удаления пользователя псоле команды надо ввести его id");
        }
        userService.delete(Long.valueOf(userId));
    }
}
