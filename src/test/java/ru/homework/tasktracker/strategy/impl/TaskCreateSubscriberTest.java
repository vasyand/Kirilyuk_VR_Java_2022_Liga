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
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.service.UserService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.homework.tasktracker.model.StrategyResponse.Status;

@ExtendWith(MockitoExtension.class)
class TaskCreateSubscriberTest {

    @Mock
    UserService userService;

    @Mock
    TaskService taskService;

    @InjectMocks
    TaskCreateStrategy taskCreateSubscriber;

    @Test
    @DisplayName("Сохранение задачи с пустыми входными данными")
    void execute_WhenEventArgsIsNull_ThenReturnBadResponse() {
        TaskEvent event = new TaskEvent("task create");
        StrategyResponse strategyResponse = taskCreateSubscriber.execute(event);
        assertEquals("Для создания задачи надо ввести ее данные в виде: " +
                "id,заголовок,описание,id пользователя,дата", strategyResponse.getMessage());
        assertEquals(Status.BAD, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Сохранение задачи с неправильным количесвтом входных данных")
    void execute_WhenInvalidNumberOfFieldsForSavingTask_ThenReturnBadResponse() {
        TaskEvent event = new TaskEvent("task create title,description");
        StrategyResponse strategyResponse = taskCreateSubscriber.execute(event);
        assertEquals("Неверное количество полей для создания задачи", strategyResponse.getMessage());
        assertEquals(Status.BAD, strategyResponse.getStatus());
    }

    @ParameterizedTest
    @MethodSource("getInvalidArguments")
    @DisplayName("Сохранение задачи с неправильно заполненными входными данными")
    void execute_WhenInvalidFieldsForSavingTask_ThenReturnBadResponse(String badEvent) {
        TaskEvent event = new TaskEvent(badEvent);
        StrategyResponse strategyResponse = taskCreateSubscriber.execute(event);
        assertEquals("Неверно введена дата или id пользователя", strategyResponse.getMessage());
        assertEquals(Status.BAD, strategyResponse.getStatus());
    }

    @Test
    @DisplayName("Сохранение задачи с нормальными входными данными")
    void execute_SuccessTest() {
        TaskEvent event = new TaskEvent("task create title,description,3,20.02.2022");
        StrategyResponse strategyResponse = taskCreateSubscriber.execute(event);
        assertEquals("Задача успешно сохранена!", strategyResponse.getMessage());
        assertEquals(Status.OK, strategyResponse.getStatus());
    }

    static Stream<String> getInvalidArguments() {
        return Stream.of(
                "task create title,description,f,20.20.2022",
                "task edit 5 title,description,3,2020202"
        );
    }
}