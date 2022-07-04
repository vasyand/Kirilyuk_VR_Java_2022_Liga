package ru.homework.tasktracker.subscriber.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.Event;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.subscriber.UserSubscriber;

@Component("user-edit")
@RequiredArgsConstructor
public class UserEditSubscriber implements UserSubscriber {
    private final UserService userService;

    @Override
    public void execute(Event event) {
        if (event.getArgs() == null) {
            throw new RuntimeException("Для редактирования пользователя надо ввести его id и данные в виде: id имя");
        }
        String[] args = event.getArgs().split(" ");
        String userId = args[0];
        User user = userService.findById(Long.valueOf(userId));
        if (args.length > 1) {
            user.setName(event.getArgs().substring(userId.length()).trim());
        }
        userService.update(user);
        System.out.format("Пользователь с id %s обновлен\n", user.getId());
    }

}
