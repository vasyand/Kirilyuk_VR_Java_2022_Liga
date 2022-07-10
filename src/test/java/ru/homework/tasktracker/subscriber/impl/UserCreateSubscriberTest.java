package ru.homework.tasktracker.subscriber.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.homework.tasktracker.model.Event;
import ru.homework.tasktracker.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserCreateSubscriberTest {
    @Mock
    UserService userService;
    @InjectMocks
    UserCreateSubscriber userCreateSubscriber;

    @Test
    @DisplayName("Сохранение пользователя с пустыми входными данными")
    void execute_WhenEventArgsIsNull_ThenThrowRuntimeException() {
        Event event = new Event("user create");
        Exception ex = assertThrows(RuntimeException.class, () -> userCreateSubscriber.execute(event));
        assertEquals("Для создания пользователя надо ввести его данные в виде: id,имя", ex.getMessage());
    }

    @Test
    @DisplayName("Сохранение пользователя с неправильным количесвтом входных данных")
    void execute_WhenInvalidNumberOfFieldsForSavingTask_ThenThrowRuntimeException() {
        Event event = new Event("user create Vasya,343,3");
        Exception ex = assertThrows(RuntimeException.class, () -> userCreateSubscriber.execute(event));
        assertEquals("Неверное количество полей для создания пользователя", ex.getMessage());
    }

    @Test
    @DisplayName("Сохранение пользователя с нормальными входными данными")
    void execute_SuccessTest() {
        Event event = new Event("task create Vasya");
        userCreateSubscriber.execute(event);
        verify(userService).save(any());
    }
}