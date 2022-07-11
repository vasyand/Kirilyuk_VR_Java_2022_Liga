package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.UserEvent;
import ru.homework.tasktracker.model.UserStrategyName;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.UserStrategy;

import static ru.homework.tasktracker.model.StrategyResponse.Status;

@Component
@RequiredArgsConstructor
public class UserEditStrategy implements UserStrategy {
    private static final int MIN_NUMBER_OF_USER_FIELDS_FOR_UPDATING = 1;
    private final UserService userService;

    @Override
    public StrategyResponse execute(UserEvent event) {
        try {
            if (event.getArgs() == null) {
                throw new RuntimeException("Для редактирования пользователя надо ввести его id и данные в виде: id имя");
            }
            String[] args = event.getArgs().split(" ");
            String userId = args[0];
            User user = userService.findById(Long.valueOf(userId));
            if (args.length > MIN_NUMBER_OF_USER_FIELDS_FOR_UPDATING) {
                String userName = event.getArgs().substring(userId.length()).trim();
                user.setName(userName);
            }
            userService.update(user);
            return new StrategyResponse("Пользователь успешно изменен!", Status.OK);
        } catch (RuntimeException e) {
            return new StrategyResponse(e.getMessage(), Status.BAD);
        }
    }

    @Override
    public UserStrategyName getStrategyName() {
        return UserStrategyName.EDIT;
    }
}
