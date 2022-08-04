package ru.homework.tasktracker.model.filter;

import lombok.Getter;
import ru.homework.tasktracker.model.TaskStatus;

import java.time.LocalDate;


@Getter
public class UserFilter {
    private String firstName;
    private String middleName;
    private String lastName;
    private TaskStatus taskStatus;
    private LocalDate to;
    private LocalDate from;
}
