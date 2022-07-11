package ru.homework.tasktracker.strategy.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.homework.tasktracker.service.TaskService;

@ExtendWith(MockitoExtension.class)
class TaskDeleteSubscriberTest {

    @Mock
    TaskService taskService;

    @InjectMocks
    TaskDeleteStrategy taskDeleteSubscriber;

//    @Test
//    @DisplayName("Удаление задачи с нормальными входными данными")
//    void execute_successTest() {
//        Event event = new Event("task delete 1");
//        taskDeleteSubscriber.execute(event);
//        verify(taskService).delete(anyLong());
//    }
//
//    @Test
//    @DisplayName("Удаление задачи без указания в команде id задачи")
//    void execute_WhenTaskIdIsNull_ThenThrowRuntimeException() {
//        Event event = new Event("task delete");
//        Exception ex = assertThrows(RuntimeException.class, () -> taskDeleteSubscriber.execute(event));
//        assertEquals("Для удаления задачи после команды надо ввести его id", ex.getMessage());
//    }
}