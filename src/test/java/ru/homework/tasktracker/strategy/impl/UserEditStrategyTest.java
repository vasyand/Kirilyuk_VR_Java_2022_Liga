package ru.homework.tasktracker.strategy.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserEditStrategyTest {

    @Mock
    UserService userService;

    @Mock
    User user;

    @InjectMocks
    UserEditStrategy userEditStrategy;

//    @Test
//    @DisplayName("Изменение пользователя с пустыми входными данными")
//    void execute_WhenEventArgsIsNull_ThenReturnBadResponse() {
//        UserEvent event = new UserEvent("user edit");
//        StrategyResponse strategyResponse = userEditStrategy.execute(event);
//        assertEquals("Для редактирования пользователя надо ввести его id и данные в виде: id имя", strategyResponse.getMessage());
//        assertEquals(Status.BAD, strategyResponse.getStatus());
//    }
//
//    @Test
//    @DisplayName("Изменение пользователя с указанием нечислового id задачи")
//    void execute_WhenUserIdIsNotValid_ThenReturnBadResponse() {
//        UserEvent event = new UserEvent("user edit fff");
//        StrategyResponse strategyResponse = userEditStrategy.execute(event);
//        assertEquals("id должен быть числовым значением", strategyResponse.getMessage());
//        assertEquals(Status.BAD, strategyResponse.getStatus());
//    }
//
//    @Test
//    @DisplayName("Изменение пользователя с нормальными входными данными")
//    void execute_SuccessTest() {
//        UserEvent event = new UserEvent("user edit 3 Borya");
//
//        when(userService.findById(anyLong())).thenReturn(user);
//
//        StrategyResponse strategyResponse = userEditStrategy.execute(event);
//        assertEquals("Пользователь успешно изменен!", strategyResponse.getMessage());
//        assertEquals(Status.OK, strategyResponse.getStatus());
//    }
}