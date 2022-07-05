package ru.homework.tasktracker.util;

import java.util.List;
import java.util.stream.Collectors;

public class MessageHelper {
    public static String createMessageFromListOfEntities(String prefix, String emptyMessage, List<?> list) {
        if (list.isEmpty()) {
            return emptyMessage;
        }
        return "\n" + prefix + list.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n", "\n", "\n"));
    }

    public static String createMessageFromEntity(Object object) {
        return "\n" + object.toString() + "\n";
    }


}
