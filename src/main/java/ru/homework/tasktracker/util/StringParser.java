package ru.homework.tasktracker.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StringParser {
    public static Long getIdFromString(String args) {
        try {
           return Long.valueOf(args);
        } catch (NumberFormatException e) {
            throw new RuntimeException("id должен быть числовым значением");
        }
    }

    public static LocalDate getDateFromString(String args) {
        try {
            return LocalDate.parse(args, DateTimeFormatter.ofPattern("d.M.y"));
        } catch (DateTimeParseException ex) {
            throw new RuntimeException("Некорректно введена дата");
        }
    }
}
