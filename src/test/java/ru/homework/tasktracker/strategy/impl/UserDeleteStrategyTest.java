package ru.homework.tasktracker.strategy.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.UserEvent;
import ru.homework.tasktracker.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.homework.tasktracker.model.StrategyResponse.Status;

@ExtendWith(MockitoExtension.class)
class UserDeleteStrategyTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserDeleteStrategy userDeleteStrategy;

    @Test
    @DisplayName("Удаление пользователя с нормальными входными данными")
    void execute_successTest() {
        UserEvent event = new UserEvent("user delete 1");
        StrategyResponse strategyResponse = userDeleteStrategy.execute(event);
        assertEquals("Пользователь успешно удален!", strategyResponse.getMessage());
        assertEquals(Status.OK, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Удаление пользователя без указания в команде id задачи")
    void execute_WhenTaskIdIsNull_ThenReturnBadResponse() {
        UserEvent event = new UserEvent("user delete");
        StrategyResponse strategyResponse = userDeleteStrategy.execute(event);
        assertEquals("Для удаления пользователя псоле команды надо ввести его id", strategyResponse.getMessage());
        assertEquals(Status.BAD, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Удаление пользователя с указанием нечислового id задачи")
    void execute_WhenUserIdIsNotValid_ThenReturnBadResponse() {
        UserEvent event = new UserEvent("task delete dfssdfsd");
        StrategyResponse strategyResponse = userDeleteStrategy.execute(event);
        assertEquals("id должен быть числовым значением", strategyResponse.getMessage());
        assertEquals(Status.BAD, strategyResponse.getStatus());
    }
}