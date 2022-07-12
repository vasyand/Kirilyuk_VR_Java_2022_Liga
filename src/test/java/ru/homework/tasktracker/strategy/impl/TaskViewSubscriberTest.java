package ru.homework.tasktracker.strategy.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.TaskEvent;
import ru.homework.tasktracker.service.TaskService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static ru.homework.tasktracker.model.StrategyResponse.Status;

@ExtendWith(MockitoExtension.class)
class TaskViewSubscriberTest {

    @Mock
    TaskService taskService;

    @InjectMocks
    TaskViewStrategy taskViewSubscriber;


    @Test
    @DisplayName("Показ всех задач")
    void execute_WhenTaskIdIsNull_ThenCallFindAllMethodAndReturnOkResponse() {
        TaskEvent event = new TaskEvent("task view");
        StrategyResponse strategyResponse = taskViewSubscriber.execute(event);
        verify(taskService).findAll();
        assertEquals(Status.OK, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Показ задачи с определенным id")
    void execute_WhenTaskIdIsNotNull_ThenCallFindByIdMethodAndReturnOkResponse() {
        TaskEvent event = new TaskEvent("task view 3");
        StrategyResponse strategyResponse = taskViewSubscriber.execute(event);
        verify(taskService).findById(anyLong());
        assertEquals(Status.OK, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Показ задач с определенным статусом")
    void execute_WhenFilterIsValid_ThenCallFindAllMethodAndReturnOkResponse() {
        TaskEvent event = new TaskEvent("task view -fs=RUN");
        StrategyResponse strategyResponse = taskViewSubscriber.execute(event);
        verify(taskService).findAll();
        assertEquals(Status.OK, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Показ задач c неправильно введенным значением статуса")
    void execute_WhenFilterIsInvalid_ThenThrowRuntimeException() {
        TaskEvent event = new TaskEvent("task view -badFilter=3");
        StrategyResponse strategyResponse = taskViewSubscriber.execute(event);
        assertEquals(String.format("Фильтра %s не существует. " +
                        "Есть пока только фильтр \"-fs=status(CREATED,RUN,COMPLETED)\"",
                "-badFilter"), strategyResponse.getMessage());
        assertEquals(Status.BAD, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Показ задач cо статусом без аргумента")
    void execute_WhenFilterWithoutArgument_ThenThrowRuntimeException() {
        TaskEvent event = new TaskEvent("task view -fs=");
        StrategyResponse strategyResponse = taskViewSubscriber.execute(event);
        assertEquals("Отсутствует аргумент для фильтра!", strategyResponse.getMessage());
        assertEquals(Status.BAD, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Показ задач c невалидным аргументом в статусе")
    void execute_WhenFilterArgumentIsInvalid_ThenThrowRuntimeException() {
        TaskEvent event = new TaskEvent("task view -fs=badStatus");
        StrategyResponse strategyResponse = taskViewSubscriber.execute(event);
        assertEquals(String.format("Статуса %s не существует!", "badStatus"), strategyResponse.getMessage());
        assertEquals(Status.BAD, strategyResponse.getStatus());
    }
}