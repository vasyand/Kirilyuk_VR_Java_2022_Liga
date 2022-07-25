package ru.homework.tasktracker.strategy.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.homework.tasktracker.service.TaskService;

@ExtendWith(MockitoExtension.class)
class TaskViewStrategyTest {

    @Mock
    TaskService taskService;

    @InjectMocks
    TaskViewStrategy taskViewStrategy;


//    @Test
//    @DisplayName("Показ всех задач")
//    void execute_WhenTaskIdIsNull_ThenCallFindAllMethodAndReturnOkResponse() {
//        TaskEvent event = new TaskEvent("task view");
//        StrategyResponse strategyResponse = taskViewStrategy.execute(event);
//        verify(taskService).findAll();
//        assertEquals(Status.OK, strategyResponse.getStatus());
//    }
//
//    @Test
//    @DisplayName("Показ задачи с определенным id")
//    void execute_WhenTaskIdIsValid_ThenCallFindByIdMethodAndReturnOkResponse() {
//        TaskEvent event = new TaskEvent("task view 3");
//        StrategyResponse strategyResponse = taskViewStrategy.execute(event);
//        verify(taskService).findById(anyLong());
//        assertEquals(Status.OK, strategyResponse.getStatus());
//    }
//
//    @Test
//    @DisplayName("Показ задач с определенным статусом")
//    void execute_WhenFilterIsValid_ThenCallFindAllMethodAndReturnOkResponse() {
//        TaskEvent event = new TaskEvent("task view -fs=RUN");
//        StrategyResponse strategyResponse = taskViewStrategy.execute(event);
//        verify(taskService).findAll();
//        assertEquals(Status.OK, strategyResponse.getStatus());
//    }
//
//    @Test
//    @DisplayName("Показ задач c неправильно введенным значением статуса")
//    void execute_WhenFilterIsInvalid_ThenReturnBadResponse() {
//        TaskEvent event = new TaskEvent("task view -badFilter=3");
//        StrategyResponse strategyResponse = taskViewStrategy.execute(event);
//        assertEquals(String.format("Фильтра %s не существует. " +
//                        "Есть пока только фильтр \"-fs=status(CREATED,RUN,COMPLETED)\"",
//                "-badFilter"), strategyResponse.getMessage());
//        assertEquals(Status.BAD, strategyResponse.getStatus());
//    }
//
//    @Test
//    @DisplayName("Показ задач cо статусом без аргумента")
//    void execute_WhenFilterWithoutArgument_ThenReturnBadResponse() {
//        TaskEvent event = new TaskEvent("task view -fs=");
//        StrategyResponse strategyResponse = taskViewStrategy.execute(event);
//        assertEquals("Отсутствует аргумент для фильтра!", strategyResponse.getMessage());
//        assertEquals(Status.BAD, strategyResponse.getStatus());
//    }
//
//    @Test
//    @DisplayName("Показ задач c невалидным аргументом в статусе")
//    void execute_WhenFilterArgumentIsInvalid_ThenReturnBadResponse() {
//        TaskEvent event = new TaskEvent("task view -fs=badStatus");
//        StrategyResponse strategyResponse = taskViewStrategy.execute(event);
//        assertEquals(String.format("Статуса %s не существует!", "badStatus"), strategyResponse.getMessage());
//        assertEquals(Status.BAD, strategyResponse.getStatus());
//    }
//
//    @Test
//    @DisplayName("Показ задачи с указанием нечислового id задачи")
//    void execute_WhenTaskIdIsNotValid_ThenReturnBadResponse() {
//        TaskEvent event = new TaskEvent("task view dfssdfsd");
//        StrategyResponse strategyResponse = taskViewStrategy.execute(event);
//        assertEquals("id должен быть числовым значением", strategyResponse.getMessage());
//        assertEquals(Status.BAD, strategyResponse.getStatus());
//    }
}