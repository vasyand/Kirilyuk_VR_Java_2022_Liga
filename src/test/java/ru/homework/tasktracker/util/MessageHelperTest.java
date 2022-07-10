package ru.homework.tasktracker.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static ru.homework.tasktracker.util.MessageHelper.createMessageFromListOfEntities;

class MessageHelperTest {

    @Test
    @DisplayName("Создание сообщения для вывода из заполненного списка сущностей")
    void createMessageFromListOfEntities_SuccessTest() {
        String emptyMessage = "\nHello\n";
        String result = createMessageFromListOfEntities("", "", List.of("Hello"));
        assertEquals(emptyMessage, result);
    }

    @Test
    @DisplayName("Создание сообщения для вывода из пустого списка сущностей")
    void createMessageFromListOfEntities_WhenListIsEmpty_ThenReturnEmptyMessage() {
        String emptyMessage = "emptyMessage";
        String result = createMessageFromListOfEntities("", emptyMessage, emptyList());
        assertEquals(emptyMessage, result);
    }

    @Test
    @DisplayName("Создание сообщения для вывода из null списка")
    void createMessageFromListOfEntities_WhenListIsNull_ThenReturnEmptyMessage() {
        String emptyMessage = "emptyMessage";
        String result = createMessageFromListOfEntities("", emptyMessage, null);
        assertEquals(emptyMessage, result);
    }

}