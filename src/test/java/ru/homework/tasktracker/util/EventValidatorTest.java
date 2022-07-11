package ru.homework.tasktracker.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.homework.tasktracker.util.EventHelper.isValidEvent;

class EventValidatorTest {

    @Test
    @DisplayName("Проверка валидности значения null")
    void isValidEvent_WhenEventIsNull() {
        boolean result = isValidEvent(null);
        assertFalse(result);
    }

//    @ParameterizedTest
//    @MethodSource("getInvalidEvents")
//    @DisplayName("Проверка невалидных значений события")
//    void isValidEvent_WhenEventIsNotValid(Event invalidEvent) {
//        boolean result = isValidEvent(invalidEvent);
//        assertFalse(result);
//    }
//
//    @ParameterizedTest
//    @MethodSource("getValidEvents")
//    @DisplayName("Проверка валидных значений события")
//    void isValidEvent_WhenEventIsValid(Event validEvent) {
//        boolean result = isValidEvent(validEvent);
//        assertTrue(result);
//    }

    private static Stream<Event> getValidEvents() {
        return Stream.of(
                new Event("user command"),
                new Event("task command")
        );
    }

    private static Stream<Event> getInvalidEvents() {
        return Stream.of(
                new Event("ываыв comman"),
                new Event("notask"),
                new Event("user"),
                new Event(""),
                new Event("3243333")
        );
    }

}