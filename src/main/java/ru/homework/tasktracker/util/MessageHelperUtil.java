package ru.homework.tasktracker.util;

import java.util.List;
import java.util.stream.Collectors;

public class MessageHelperUtil {
    public static String createMessageFromListOfEntities(String prefix, String emptyMessage, List<?> list) {
        if (prefix == null) {
            prefix = "";
        }
        if (emptyMessage == null) {
            emptyMessage = "";
        }
        if (list == null || list.isEmpty()) {
            return emptyMessage;
        }
        return "\n" + prefix + list.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n", "", "\n"));
    }

    public static String createMessageFromEntity(Object object) {
        if (object == null) return  "";
        return createMessageFromListOfEntities("\n", "", List.of(object));
    }


}
