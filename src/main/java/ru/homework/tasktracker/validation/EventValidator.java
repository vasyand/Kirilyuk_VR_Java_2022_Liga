package ru.homework.tasktracker.validation;

import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.Event;

@Component
public class EventValidator {

    public boolean isValidEvent(Event event) {
        if (event.getCommand() == null) {
            System.out.println("Кроме ключевого слова надо еще ввести комманду");
            return false;
        }
        if (!event.getType().equals("user") && !event.getType().equals("task")) {
            System.out.println("Такого типа команд нет. Все команды должны начинаться со слов \"user\" или \"task\"");
            return false;
        }
        return true;
    }
}
