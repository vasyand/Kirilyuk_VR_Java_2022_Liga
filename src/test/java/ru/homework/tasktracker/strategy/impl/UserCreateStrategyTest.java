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
import static ru.homework.tasktracker.model.StrategyResponse.*;

@ExtendWith(MockitoExtension.class)
class UserCreateStrategyTest {
    @Mock
    UserService userService;
    @InjectMocks
    UserCreateStrategy userCreateStrategy;

    @Test
    @DisplayName("Сохранение пользователя с пустыми входными данными")
    void execute_WhenEventArgsIsNull_ThenReturnBadResponse() {
        UserEvent event = new UserEvent("user create");
        StrategyResponse strategyResponse = userCreateStrategy.execute(event);
        assertEquals("Для создания пользователя надо ввести его данные в виде: id,имя", strategyResponse.getMessage());
        assertEquals(Status.BAD, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Сохранение пользователя с неправильным количесвтом входных данных")
    void execute_WhenInvalidNumberOfFieldsForSavingTask_ThenReturnBadResponse() {
        UserEvent event = new UserEvent("user create Vasya,343,3");
        StrategyResponse strategyResponse = userCreateStrategy.execute(event);
        assertEquals("Неверное количество полей для создания пользователя", strategyResponse.getMessage());
        assertEquals(Status.BAD, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Сохранение пользователя с нормальными входными данными")
    void execute_SuccessTest() {
        UserEvent event = new UserEvent("task create Vasya");
        StrategyResponse strategyResponse = userCreateStrategy.execute(event);
        assertEquals("Пользователь успешно сохранен!", strategyResponse.getMessage());
        assertEquals(Status.OK, strategyResponse.getStatus());
    }
}