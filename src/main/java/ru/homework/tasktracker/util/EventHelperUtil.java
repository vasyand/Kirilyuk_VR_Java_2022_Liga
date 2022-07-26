package ru.homework.tasktracker.util;

import lombok.extern.slf4j.Slf4j;
import ru.homework.tasktracker.model.TaskStrategyName;
import ru.homework.tasktracker.model.UserStrategyName;

@Slf4j
public class EventHelperUtil {
    private final static int NUMBER_EVENT_FIELDS_WITHOUT_COMMAND = 1;

    public static boolean isValidEvent(String event) {
        if (event == null) {
            log.error("Событие пустое");
            return false;
        }
        String[] eventArgs = event.split(" ");
        String type = eventArgs[0];
        if (!type.equals("user") && !type.equals("task")) {
            log.error("Такого типа запроса нет. Все запросы должны начинаться со слов \"user\" или \"task\"");
            return false;
        }
        if (eventArgs.length == NUMBER_EVENT_FIELDS_WITHOUT_COMMAND) {
            log.error("Кроме ключевого слова надо еще ввести комманду");
            return false;
        }
        String strategyName = eventArgs[1].toUpperCase();
        try {
            if (type.equals("user")) UserStrategyName.valueOf(strategyName);
            if (type.equals("task")) TaskStrategyName.valueOf(strategyName);
        } catch (IllegalArgumentException e) {
            log.error("Такой команды не существует");
            return false;
        }
        return true;
    }

    public static boolean isUserStrategy(String eventString) {
        return eventString.startsWith("user");
    }
}
