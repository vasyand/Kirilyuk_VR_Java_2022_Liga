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
class TaskCreateSubscriberTest {

    @Mock
    UserService userService;

    @Mock
    TaskService taskService;

    @InjectMocks
    TaskCreateSubscriber taskCreateSubscriber;

    @Test
    @DisplayName("Сохранение задачи с пустыми входными данными")
    void execute_WhenEventArgsIsNull_ThenThrowRuntimeException() {
        Event event = new Event("task create");
        Exception ex = assertThrows(RuntimeException.class, () -> taskCreateSubscriber.execute(event));
        assertEquals("Для создания задачи надо ввести ее данные в виде: " +
                "id,заголовок,описание,id пользователя,дата", ex.getMessage());
    }

    @Test
    @DisplayName("Сохранение задачи с неправильным количесвтом входных данных")
    void execute_WhenInvalidNumberOfFieldsForSavingTask_ThenThrowRuntimeException() {
        Event event = new Event("task create title,description");
        Exception ex = assertThrows(RuntimeException.class, () -> taskCreateSubscriber.execute(event));
        assertEquals("Неверное количество полей для создания задачи", ex.getMessage());
    }

    @Test
    @DisplayName("Сохранение задачи с нормальными входными данными")
    void execute_SuccessTest() {
        Event event = new Event("task create title,description,3,20.02.2022");
        taskCreateSubscriber.execute(event);
        verify(userService).findById(anyLong());
        verify(taskService).save(any());
    }
}