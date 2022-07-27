package ru.homework.tasktracker.model.filter;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectFilter {
    private String title;
    private String description;

    public void setFilter(String filter, String argument) {
        switch (filter) {
            case "title" -> title = argument;
            case "description" -> description = argument;
            default -> throw new RuntimeException("Такого фильтра пока еще нет");
        }
    }
}
