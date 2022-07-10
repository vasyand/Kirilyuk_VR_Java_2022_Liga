package ru.homework.tasktracker.util;

import lombok.extern.slf4j.Slf4j;
import ru.homework.tasktracker.model.Event;

@Slf4j
public class EventValidator {

    public static boolean isValidEvent(Event event) {
        if (event == null) {
            log.error("Событие пустое");
            return false;
        }
        if (event.getCommand() == null) {
            log.error("Кроме ключевого слова надо еще ввести комманду");
            return false;
        }
        if (!event.getType().equals("user") && !event.getType().equals("task")) {
            log.error("Такого типа команд нет. Все команды должны начинаться со слов \"user\" или \"task\"");
            return false;
        }
        return true;
    }
}
