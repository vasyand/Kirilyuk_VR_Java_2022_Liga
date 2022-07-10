package ru.homework.tasktracker.subscriber.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.homework.tasktracker.model.Event;
import ru.homework.tasktracker.service.TaskService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaskEditSubscriberTest {

    @Mock
    TaskService taskService;

    @InjectMocks
    TaskEditSubscriber taskEditSubscriber;

    @Test
    @DisplayName("Изменение задачи с пустыми входными данными")
    void execute_WhenEventArgsIsNull_ThenThrowRuntimeException() {
        Event event = new Event("task edit");
        Exception ex = assertThrows(RuntimeException.class, () -> taskEditSubscriber.execute(event));
        assertEquals("Для редактирования задачи надо ввести его id и данные в виде: " +
                "id заголовок,описание,id пользователя,дата,статус", ex.getMessage());
    }

    @Test
    @DisplayName("Изменение задачи с нормальными входными данными")
    void execute_SuccessTest() {
        Event event = new Event("task edit 3");
        taskEditSubscriber.execute(event);
        verify(taskService).update(any());
    }
}