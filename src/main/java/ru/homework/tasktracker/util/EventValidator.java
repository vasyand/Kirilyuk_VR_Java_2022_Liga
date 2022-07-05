package ru.homework.tasktracker.util;

import lombok.extern.slf4j.Slf4j;
import ru.homework.tasktracker.model.Event;

@Slf4j
public class EventValidator {

    public static boolean isValidEvent(Event event) {
        if (event.getCommand() == null) {
            log.error("Кроме ключевого слова надо еще ввести комманду");
            return false;
        }
        if (!event.getType().equals("user")
                && !event.getType().equals("task")
                && !event.getType().equals("help")) {
            log.error("Такого типа команд нет. Все команды должны начинаться со слов \"user\", \"task\" или \"help\"");
            return false;
        }
        return true;
    }
}
