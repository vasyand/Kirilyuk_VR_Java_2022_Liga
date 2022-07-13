package ru.homework.tasktracker.strategy.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.UserEvent;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.homework.tasktracker.model.StrategyResponse.*;

@ExtendWith(MockitoExtension.class)
class UserViewTaskStrategyTest {

    @Mock
    UserService userService;

    @Mock
    User user;

    @InjectMocks
    UserViewTaskStrategy userViewTaskStrategy;

    @Test
    @DisplayName("Показ задач пользователя без фильтра")
    void execute_WhenUserIdIsValid_ThenCallFindByIdMethodAndReturnOkResponse() {
        UserEvent event = new UserEvent("user viewt 3");
        when(userService.findById(anyLong())).thenReturn(user);
        StrategyResponse strategyResponse = userViewTaskStrategy.execute(event);
        verify(userService).findById(anyLong());
        assertEquals(Status.OK, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Показ задач пользователя c фильтром")
    void execute_WhenUserIdAndFilterAreValid_ThenReturnOkResponse() {
        UserEvent event = new UserEvent("user viewt 3 -fs=RUN");
        when(userService.findById(anyLong())).thenReturn(user);
        StrategyResponse strategyResponse = userViewTaskStrategy.execute(event);
        assertEquals(Status.OK, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Показ задач пользователя с указанием нечислового id пользователя")
    void execute_WhenUserIdIsNotValid_ThenReturnBadResponse() {
        UserEvent event = new UserEvent("user viewt ggg");
        StrategyResponse strategyResponse = userViewTaskStrategy.execute(event);
        assertEquals("id должен быть числовым значением", strategyResponse.getMessage());
        assertEquals(Status.BAD, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Показ задач пользователя без введенного id пользователя")
    void execute_WhenUserIdIsNull_ThenReturnBadResponse() {
        UserEvent event = new UserEvent("user viewt");
        StrategyResponse strategyResponse = userViewTaskStrategy.execute(event);
        assertEquals("Для команды \"viewt\" надо указать " +
                "id пользователя и фильтр статуса задачи (опционально)", strategyResponse.getMessage());
        assertEquals(Status.BAD, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Показ задач пользователя c неправильно введенным значением статуса")
    void execute_WhenFilterIsInvalid_ThenReturnBadResponse() {
        UserEvent event = new UserEvent("user viewt 4 -badFilter=3");
        StrategyResponse strategyResponse = userViewTaskStrategy.execute(event);
        assertEquals(
                String.format("Фильтра %s не существует. " +
                        "Есть пока только фильтр \"-fs=status(CREATED,RUN,COMPLETED)\"", "-badFilter"),
                strategyResponse.getMessage());
        assertEquals(Status.BAD, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Показ задач пользователя cо статусом без аргумента")
    void execute_WhenFilterWithoutArgument_ThenReturnBadResponse() {
        UserEvent event = new UserEvent("user viewt 4 -fs=");
        StrategyResponse strategyResponse = userViewTaskStrategy.execute(event);
        assertEquals("А параметр для фильтра-то кто вводить будет????", strategyResponse.getMessage());
        assertEquals(Status.BAD, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Показ задач пользователя c невалидным аргументом в статусе")
    void execute_WhenFilterArgumentIsInvalid_ThenReturnBadResponse() {
        UserEvent event = new UserEvent("user viewt 3 -fs=badStatus");
        StrategyResponse strategyResponse = userViewTaskStrategy.execute(event);
        assertEquals(String.format("Статуса %s не существует!", "badStatus"), strategyResponse.getMessage());
        assertEquals(Status.BAD, strategyResponse.getStatus());
    }
}