package ru.homework.tasktracker.subscriber.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.homework.tasktracker.model.Event;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserViewTaskSubscriberTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserViewTaskSubscriber userViewTaskSubscriber;

    @Test
    @DisplayName("Показ задач пользователя с определенным статусом")
    void execute_successTest() {
        Event event = new Event("user viewt 3 ");
        User user = new User();
        when(userService.findById(anyLong())).thenReturn(user);
        userViewTaskSubscriber.execute(event);
        verify(userService).findById(anyLong());
    }

    @Test
    @DisplayName("Показ задач пользователя без введенного id пользователя")
    void execute_WhenUserIdIsNull_ThenThrowRuntimeException() {
        Event event = new Event("user viewt");
        Exception ex = assertThrows(RuntimeException.class, () -> userViewTaskSubscriber.execute(event));
        assertEquals("Для команды \"viewt\" надо указать " +
                "id пользователя и фильтр статуса задачи (опционально)", ex.getMessage());
    }

    @Test
    @DisplayName("Показ задач пользователя c неправильно введенным значением статуса")
    void execute_WhenFilterIsInvalid_ThenThrowRuntimeException() {
        Event event = new Event("user viewt 4 -badFilter=3");
        Exception ex = assertThrows(RuntimeException.class, () -> userViewTaskSubscriber.execute(event));
        assertEquals(String.format("Фильтра %s не существует. " +
                "Есть пока только фильтр \"-fs=status(CREATED,RUN,COMPLETED)\"", "-badFilter"), ex.getMessage());
    }

    @Test
    @DisplayName("Показ задач пользователя cо статусом без аргумента")
    void execute_WhenFilterWithoutArgument_ThenThrowRuntimeException() {
        Event event = new Event("user viewt 4 -fs=");
        Exception ex = assertThrows(RuntimeException.class, () -> userViewTaskSubscriber.execute(event));
        assertEquals("А параметр для фильтра-то кто вводить будет????", ex.getMessage());
    }

    @Test
    @DisplayName("Показ задач пользователя c невалидным аргументом в статусе")
    void execute_WhenFilterArgumentIsInvalid_ThenThrowRuntimeException() {
        Event event = new Event("user viewt 3 -fs=badStatus");
        Exception ex = assertThrows(RuntimeException.class, () -> userViewTaskSubscriber.execute(event));
        assertEquals(String.format("Статуса %s не существует!", "badStatus"), ex.getMessage());
    }
}