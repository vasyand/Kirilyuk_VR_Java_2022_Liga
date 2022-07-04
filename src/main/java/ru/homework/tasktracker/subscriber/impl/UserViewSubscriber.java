package ru.homework.tasktracker.subscriber.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.Event;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.subscriber.UserSubscriber;

import java.util.List;

@Component("user-view")
@RequiredArgsConstructor
public class UserViewSubscriber implements UserSubscriber {
    private final UserService userService;

    @Override
    public void execute(Event event) {
        String userId = event.getArgs();
        if (userId == null) {
            List<User> users = userService.findAll();
            if (users.isEmpty()) {
                throw new RuntimeException("Пользователей в бд вообще нет((");
            }
            System.out.println("Список всех пользователей: ");
            users.forEach(System.out::println);
        } else {
            User user = userService.findById(Long.valueOf(userId));
            System.out.println(user);
        }
    }

}
