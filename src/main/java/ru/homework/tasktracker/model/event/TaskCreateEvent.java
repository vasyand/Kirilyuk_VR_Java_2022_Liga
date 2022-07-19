package ru.homework.tasktracker.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TaskCreateEvent {
    private final String title;
    private final String description;
    private final Long userId;
    private final LocalDate date;
}
