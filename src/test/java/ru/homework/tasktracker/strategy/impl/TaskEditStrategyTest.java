package ru.homework.tasktracker.strategy.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.TaskEvent;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.service.UserService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static ru.homework.tasktracker.model.StrategyResponse.Status;

@ExtendWith(MockitoExtension.class)
class TaskEditStrategyTest {

    @Mock
    TaskService taskService;

    @Mock
    UserService userService;

    @Mock
    Task task;

    @InjectMocks
    TaskEditStrategy taskEditStrategy;

    @Test
    @DisplayName("Изменение задачи с пустыми входными данными")
    void execute_WhenEventArgsIsNull_ThenReturnBadResponse() {
        TaskEvent event = new TaskEvent("task edit");
        StrategyResponse strategyResponse = taskEditStrategy.execute(event);
        assertEquals("Для редактирования задачи надо ввести его id и данные в виде: " +
                "id заголовок,описание,id пользователя,дата,статус", strategyResponse.getMessage());
        assertEquals(Status.BAD, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Изменение задачи с неправильным количеством входных данными")
    void execute_WhenNumberArgsForUpdatingIsNotValid_ThenReturnBadResponse() {
        TaskEvent event = new TaskEvent("task edit 3 e,f,");
        StrategyResponse strategyResponse = taskEditStrategy.execute(event);
        assertEquals(
                "Неправильное количество введенных для обновления полей." +
                        "Если какие-то поля не обновляются, их надо заменить точками, напирмер: .,.,.,.,RUN",
                strategyResponse.getMessage());
        assertEquals(Status.BAD, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Изменение задачи с указанием нечислового id задачи")
    void execute_WhenTaskIdIsNotValid_ThenReturnBadResponse() {
        TaskEvent event = new TaskEvent("task edit ffff");
        StrategyResponse strategyResponse = taskEditStrategy.execute(event);
        assertEquals("Неверно введена дата или id пользователя/задачи", strategyResponse.getMessage());
        assertEquals(Status.BAD, strategyResponse.getStatus());
    }

    @ParameterizedTest
    @MethodSource("getInvalidArguments")
    @DisplayName("Изменение задачи с неправильно введенными аргументами")
    void execute_WhenArgsAreInvalid_ThenReturnBadResponse(String badEvent) {
        TaskEvent event = new TaskEvent(badEvent);

        when(taskService.findById(anyLong())).thenReturn(task);

        StrategyResponse strategyResponse = taskEditStrategy.execute(event);
        assertEquals("Неверно введена дата или id пользователя/задачи", strategyResponse.getMessage());
        assertEquals(Status.BAD, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Изменение задачи с нормальными входными данными")
    void execute_SuccessTest() {
        TaskEvent event = new TaskEvent("task edit 3 .,.,.,.,RUN");

        when(taskService.findById(anyLong())).thenReturn(task);

        StrategyResponse strategyResponse = taskEditStrategy.execute(event);
        assertEquals("Задача успешно обновлена!", strategyResponse.getMessage());
        assertEquals(Status.OK, strategyResponse.getStatus());
    }

    static Stream<String> getInvalidArguments() {
        return Stream.of(
                "task edit 5 title,description,f,2020202,RUN",
                "task edit 5 title,description,3,2020202,RUN"
        );
    }
}