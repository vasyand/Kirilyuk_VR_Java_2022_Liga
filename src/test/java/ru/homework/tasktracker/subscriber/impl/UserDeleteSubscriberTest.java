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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserDeleteSubscriberTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserDeleteSubscriber userDeleteSubscriber;

    @Test
    @DisplayName("Удаление пользователя с нормальными входными данными")
    void execute_successTest() {
        Event event = new Event("user delete 1");
        userDeleteSubscriber.execute(event);
        verify(userService).delete(anyLong());
    }

    @Test
    @DisplayName("Удаление пользователя без указания в команде id задачи")
    void execute_WhenTaskIdIsNull_ThenThrowRuntimeException() {
        Event event = new Event("user delete");
        Exception ex = assertThrows(RuntimeException.class, () -> userDeleteSubscriber.execute(event));
        assertEquals("Для удаления пользователя псоле команды надо ввести его id", ex.getMessage());
    }
}