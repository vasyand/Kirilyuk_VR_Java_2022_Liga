package ru.homework.tasktracker.strategy.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.homework.tasktracker.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserViewStrategyTest {

    @Mock
    UserService userService;
    @InjectMocks
    UserViewStrategy userViewStrategy;

//    @Test
//    @DisplayName("Показ всех пользователей")
//    void execute_WhenUserIdIsNull_ThenCallFindAllMethodAndReturnOkResponse() {
//        UserEvent event = new UserEvent("user view");
//        StrategyResponse strategyResponse = userViewStrategy.execute(event);
//        verify(userService).findAll();
//        assertEquals(StrategyResponse.Status.OK, strategyResponse.getStatus());
//    }
//
//    @Test
//    @DisplayName("Показ пользователя с определенным id")
//    void execute_WhenUserIdIsNotNull_ThenCallFindByIdMethodAndReturnOkResponse() {
//        UserEvent event = new UserEvent("user view 3");
//        StrategyResponse strategyResponse = userViewStrategy.execute(event);
//        verify(userService).findById(anyLong());
//        assertEquals(StrategyResponse.Status.OK, strategyResponse.getStatus());
//    }
//
//    @Test
//    @DisplayName("Показ пользователя с указанием нечислового id задачи")
//    void execute_WhenUserIdIsNotValid_ThenReturnBadResponse() {
//        UserEvent event = new UserEvent("user edit fff");
//        StrategyResponse strategyResponse = userViewStrategy.execute(event);
//        assertEquals("id должен быть числовым значением", strategyResponse.getMessage());
//        assertEquals(StrategyResponse.Status.BAD, strategyResponse.getStatus());
//    }
}