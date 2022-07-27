package ru.homework.tasktracker.model.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentFilter {
    private String message;
    private String taskId;

    public void setFilter(String filter, String argument) {
        switch (filter) {
            case "message" -> message = argument;
            case "task" -> taskId = argument;
            default -> throw new RuntimeException("Такого фильтра пока еще нет");
        }
    }
}
