package ru.homework.tasktracker.subscriber.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.homework.tasktracker.model.Event;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskDeleteSubscriberTest {

    @Mock
    TaskService taskService;

    @InjectMocks
    TaskDeleteSubscriber taskDeleteSubscriber;

    @Test
    @DisplayName("Удаление задачи с нормальными входными данными")
    void execute_successTest() {
        Event event = new Event("task delete 1");
        taskDeleteSubscriber.execute(event);
        verify(taskService).delete(anyLong());
    }

    @Test
    @DisplayName("Удаление задачи без указания в команде id задачи")
    void execute_WhenTaskIdIsNull_ThenThrowRuntimeException() {
        Event event = new Event("task delete");
        Exception ex = assertThrows(RuntimeException.class, () -> taskDeleteSubscriber.execute(event));
        assertEquals("Для удаления задачи после команды надо ввести его id", ex.getMessage());
    }
}