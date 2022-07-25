package ru.homework.tasktracker.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.homework.tasktracker.util.EventHelper.isValidEvent;

class EventHelperTest {

    @Test
    @DisplayName("Проверка валидности значения null")
    void isValidEvent_WhenEventIsNull() {
        boolean result = isValidEvent(null);
        assertFalse(result);
    }

    @ParameterizedTest
    @MethodSource("getInvalidEvents")
    @DisplayName("Проверка невалидных значений события")
    void isValidEvent_WhenEventIsNotValid(String invalidEvent) {
        boolean result = isValidEvent(invalidEvent);
        assertFalse(result);
    }

    @ParameterizedTest
    @MethodSource("getValidEvents")
    @DisplayName("Проверка валидных значений события")
    void isValidEvent_WhenEventIsValid(String validEvent) {
        boolean result = isValidEvent(validEvent);
        assertTrue(result);
    }

    private static Stream<String> getValidEvents() {
        return Stream.of(
                "user view",
                "task create"
        );
    }

    private static Stream<String> getInvalidEvents() {
        return Stream.of(
                "ываыв comman",
                "notask",
                "user somecommand",
                "task somecommand",
                "",
                "3243333"
        );
    }

}