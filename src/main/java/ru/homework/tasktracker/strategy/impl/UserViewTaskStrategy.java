package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.*;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.entity.TaskStatus;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.UserStrategy;

import java.util.List;
import java.util.stream.Collectors;

import static ru.homework.tasktracker.model.StrategyResponse.*;
import static ru.homework.tasktracker.util.MessageHelper.createMessageFromListOfEntities;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserViewTaskStrategy implements UserStrategy {
    private final static String STATUS_FILTER = "-fs";
    private final UserService userService;

    @Override
    public StrategyResponse execute(UserEvent event) {
        try {
            if (event.getArgs() == null) {
                throw new RuntimeException("Для команды \"viewt\" надо указать " +
                        "id пользователя и фильтр статуса задачи (опционально)");
            }
            String[] args = event.getArgs().split(" ");
            User user = userService.findById(Long.valueOf(args[0]));
            StrategyResponse strategyResponse = new StrategyResponse(Status.OK);
            if (filterIsAbsent(args)) {
                strategyResponse.setMessage(createMessageFromListOfEntities(
                        String.format("Список задач у пользователя %s: ", user.getName()),
                        String.format("У пользователя %s нет задач", user.getName()),
                        user.getTasks()));
            } else {
                viewWithFilter(user, args[1], strategyResponse);
            }
            return strategyResponse;
        } catch (RuntimeException e) {
            return new StrategyResponse(e.getMessage(), Status.BAD);
        }

    }

    private void viewWithFilter(User user, String filterString, StrategyResponse strategyResponse) {
        Filter filter = new Filter(filterString);
        if (!filter.getFilter().equals(STATUS_FILTER)) {
            throw new RuntimeException(String.format("Фильтра %s не существует. " +
                    "Есть пока только фильтр \"-fs=status(CREATED,RUN,COMPLETED)\"", filter.getFilter()));
        } else if (filter.getArgument() == null) {
            throw new RuntimeException("А параметр для фильтра-то кто вводить будет????");
        }
        String status = filter.getArgument();
        if (!TaskStatus.getStatusSet().contains(status)) {
            throw new RuntimeException(String.format("Статуса %s не существует!", status));
        }
        List<Task> tasks = user.getTasks().stream()
                .filter(task -> task.getTaskStatus().toString().equals(status))
                .collect(Collectors.toList());
        strategyResponse.setMessage(createMessageFromListOfEntities(
                String.format("Список задач со статусом %s у пользователя %s: ", status, user.getName()),
                String.format("Задач со статусом %s у пользователя %s сейчас нет ", status, user.getName()),
                tasks));
    }

    private boolean filterIsAbsent(String[] arg) {
        return arg.length == 1;
    }

    @Override
    public UserStrategyName getStrategyName() {
        return UserStrategyName.VIEWT;
    }
}
