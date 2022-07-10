package ru.homework.tasktracker.subscriber.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.homework.tasktracker.model.Event;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserEditSubscriberTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserEditSubscriber userEditSubscriber;

    @Test
    @DisplayName("Изменение пользователя с пустыми входными данными")
    void execute_WhenEventArgsIsNull_ThenThrowRuntimeException() {
        Event event = new Event("user edit");
        Exception ex = assertThrows(RuntimeException.class, () -> userEditSubscriber.execute(event));
        assertEquals("Для редактирования пользователя надо ввести его id и данные в виде: id имя", ex.getMessage());
    }

    @Test
    @DisplayName("Изменение задачи с нормальными входными данными")
    void execute_SuccessTest() {
        Event event = new Event("user edit 3");
        userEditSubscriber.execute(event);
        verify(userService).update(any());
    }
}