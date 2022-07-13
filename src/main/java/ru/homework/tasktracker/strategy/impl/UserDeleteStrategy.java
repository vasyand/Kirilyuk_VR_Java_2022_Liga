package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.UserEvent;
import ru.homework.tasktracker.model.UserStrategyName;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.UserStrategy;

import static ru.homework.tasktracker.model.StrategyResponse.*;

@Component
@RequiredArgsConstructor
public class UserDeleteStrategy implements UserStrategy {
    private final UserService userService;

    @Override
    public StrategyResponse execute(UserEvent event) {
        try {
            String userId = event.getArgs();
            if (userId == null) {
                throw new RuntimeException("Для удаления пользователя псоле команды надо ввести его id");
            }
            userService.delete(Long.valueOf(userId));
            return new StrategyResponse("Пользователь успешно удален!", Status.OK);
        } catch (NumberFormatException e) {
            return new StrategyResponse("id должен быть числовым значением", Status.BAD);
        } catch (RuntimeException e) {
            return new StrategyResponse(e.getMessage(), Status.BAD);
        }
    }

    @Override
    public UserStrategyName getStrategyName() {
        return UserStrategyName.DELETE;
    }
}
