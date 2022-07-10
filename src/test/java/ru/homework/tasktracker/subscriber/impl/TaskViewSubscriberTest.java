package ru.homework.tasktracker.subscriber.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.homework.tasktracker.model.Event;
import ru.homework.tasktracker.service.TaskService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaskViewSubscriberTest {

    @Mock
    TaskService taskService;

    @InjectMocks
    TaskViewSubscriber taskViewSubscriber;


    @Test
    @DisplayName("Показ всех задач")
    void execute_WhenTaskIdIsNull_ThenCallFindAllMethod() {
        Event event = new Event("task view");
        taskViewSubscriber.execute(event);
        verify(taskService).findAll();
    }

    @Test
    @DisplayName("Показ задачи с определенным id")
    void execute_WhenTaskIdIsNotNull_ThenCallFindByIdMethod() {
        Event event = new Event("task view 3");
        taskViewSubscriber.execute(event);
        verify(taskService).findById(anyLong());
    }

    @Test
    @DisplayName("Показ задач с определенным статусом")
    void execute_WhenFilterIsValid_ThenCallFindAllMethod() {
        Event event = new Event("task view -fs=RUN");
        taskViewSubscriber.execute(event);
        verify(taskService).findAll();
    }

    @Test
    @DisplayName("Показ задач c неправильно введенным значением статуса")
    void execute_WhenFilterIsInvalid_ThenThrowRuntimeException() {
        Event event = new Event("task view -badFilter=3");
        Exception ex = assertThrows(RuntimeException.class, () -> taskViewSubscriber.execute(event));
        assertEquals(String.format("Фильтра %s не существует. " +
                        "Есть пока только фильтр \"-fs=status(CREATED,RUN,COMPLETED)\"",
                "-badFilter"), ex.getMessage());
    }

    @Test
    @DisplayName("Показ задач cо статусом без аргумента")
    void execute_WhenFilterWithoutArgument_ThenThrowRuntimeException() {
        Event event = new Event("task view -fs=");
        Exception ex = assertThrows(RuntimeException.class, () -> taskViewSubscriber.execute(event));
        assertEquals("Отсутствует аргумент для фильтра!", ex.getMessage());
    }

    @Test
    @DisplayName("Показ задач c невалидным аргументом в статусе")
    void execute_WhenFilterArgumentIsInvalid_ThenThrowRuntimeException() {
        Event event = new Event("task view -fs=badStatus");
        Exception ex = assertThrows(RuntimeException.class, () -> taskViewSubscriber.execute(event));
        assertEquals(String.format("Статуса %s не существует!", "badStatus"), ex.getMessage());
    }
}