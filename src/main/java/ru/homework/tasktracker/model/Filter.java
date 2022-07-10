package ru.homework.tasktracker.model;

import lombok.Getter;

@Getter
public class Filter {
    private final String filter;
    private String argument;

    public Filter(String filterString) {
        String[] filterWithArg = filterString.split("=");
        this.filter = filterWithArg[0];
        if (filterWithArg.length != 1)
        this.argument = filterWithArg[1];
    }
}
