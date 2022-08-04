package ru.homework.tasktracker.model;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum TaskStatus {
    CREATED ("Создана"),
    RUN("В работе"),
    COMPLETED("Завершена");

    private final String description;

    TaskStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Set<String> getStatusSet() {
        return Arrays.stream(values())
                .map(Enum::toString)
                .collect(Collectors.toSet());
    }
}


