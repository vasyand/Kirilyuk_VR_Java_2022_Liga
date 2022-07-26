package ru.homework.tasktracker.model.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.model.entity.TaskStatus;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.util.StringParserUtil;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static ru.homework.tasktracker.util.StringParserUtil.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskFilter {
    private String title;
    private String description;
    private String userId;
    private String projectId;
    private TaskStatus taskStatus;
    private LocalDate to;
    private LocalDate from;

    public void setFilter(String filter, String argument) {
        switch (filter) {
            case "status" -> taskStatus = TaskStatus.valueOf(argument);
            case "title" -> title = argument;
            case "description" -> description = argument;
            case "user" -> userId = argument;
            case "project" -> projectId = argument;
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
