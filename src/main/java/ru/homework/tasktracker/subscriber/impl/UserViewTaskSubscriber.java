package ru.homework.tasktracker.subscriber.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.Event;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.entity.TaskStatus;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.subscriber.UserSubscriber;

import java.util.List;
import java.util.stream.Collectors;

@Component("user-viewt")
@RequiredArgsConstructor
public class UserViewTaskSubscriber implements UserSubscriber {
    private final UserService userService;

    @Override
    public void execute(Event event) {
        if (event.getArgs() == null) {
            throw new RuntimeException("Для команды \"viewt\" надо указать id пользователя и фильтр статуса задачи (опционально)");
        }
        String[] args = event.getArgs().split(" ");
        User user = userService.findById(Long.valueOf(args[0]));
        if (args.length == 1) {
            if (user.getTasks().isEmpty()) {
                throw new RuntimeException(String.format("У пользователя %s нет задач", user.getName()));
            }
            System.out.format("Список задач у пользователя %s:\n", user.getName());
            user.getTasks().forEach(System.out::println);
        } else {
            viewWithFilter(user, args[1]);
        }
    }

    private void viewWithFilter(User user, String args) {
        String[] filterWithArg = args.split("=");
        String filter = filterWithArg[0];
        if (!filter.equals("-fs")) {
            throw new RuntimeException(String.format("Фильтра %s не существует. Есть пока только фильтр \"-fs=status(CREATED,RUN,COMPLETED)\"\n", filter));
        } else if (filterWithArg.length == 1) {
            throw new RuntimeException("А параметр для фильтра-то кто вводить будет????");
        }
        String status = filterWithArg[1].trim();
        if (!TaskStatus.getStatusSet().contains(status)) {
            throw new RuntimeException(String.format("Статуса %s не существует!", status));
        }
        List<Task> tasks = user.getTasks().stream()
                .filter(task -> task.getTaskStatus().toString().equals(status))
                .collect(Collectors.toList());
        if (tasks.isEmpty()) {
            throw new RuntimeException(String.format("Задач со статусом %s у пользователя %s сейчас нет\n", status, user.getName()));
        }
        System.out.format("Список задач со статусом %s у пользователя %s:\n", status, user.getName());
        tasks.forEach(System.out::println);

    }
}
