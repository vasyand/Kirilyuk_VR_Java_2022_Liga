package ru.homework.tasktracker.strategy.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.homework.tasktracker.service.UserService;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserViewSubscriberTest {

    @Mock
    UserService userService;
    @InjectMocks
    UserViewStrategy userViewSubscriber;

//    @Test
//    @DisplayName("Показ всех пользователей")
//    void execute_WhenUserIdIsNull_ThenCallFindAllMethod() {
//        Event event = new Event("user view");
//        userViewSubscriber.execute(event);
//        verify(userService).findAll();
//    }
//
//    @Test
//    @DisplayName("Показ пользователя с определенным id")
//    void execute_WhenUserIdIsNotNull_ThenCallFindByIdMethod() {
//        Event event = new Event("user view 3");
//        userViewSubscriber.execute(event);
//        verify(userService).findById(anyLong());
//    }
}