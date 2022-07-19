package ru.homework.tasktracker.model.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.homework.tasktracker.model.entity.TaskStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class TaskFilter {

    private TaskStatus taskStatus;
    private LocalDate to;
    private LocalDate from;

    public void setFilter(String filter, String argument) {
        switch (filter) {
            case "status" -> taskStatus = TaskStatus.valueOf(argument);
            case "date" -> setDate(argument);
            default -> throw new RuntimeException("Такого фильтра пока еще нет");
        }
    }

    private void setDate(String argument) {
        try {
            String[] dates = argument.split(",");
            from = LocalDate.parse(dates[0], DateTimeFormatter.ofPattern("d.M.y"));
            to = LocalDate.parse(dates[1], DateTimeFormatter.ofPattern("d.M.y"));
        } catch (Exception e) {
            throw new RuntimeException("Филльтр с датами задан некорректно");
        }
    }
}
