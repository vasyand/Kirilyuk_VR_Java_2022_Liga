package ru.homework.tasktracker.mapper;

import ru.homework.tasktracker.model.event.*;
import ru.homework.tasktracker.model.filter.UserFilter;

import static ru.homework.tasktracker.util.StringParser.getIdFromString;


public class UserEventMapper {

    public static UserCreateEvent toUserCreateEvent(String args) {
        if (args == null) {
            throw new RuntimeException("Для создания пользователя надо ввести его данные в виде: имя");
        }
        return new UserCreateEvent(args);
    }

    public static UserDeleteEvent toUserDeleteEvent(String args) {
        if (args == null) {
            throw new RuntimeException("Для удаления пользователя псоле команды надо ввести его id");
        }
        return new UserDeleteEvent(getIdFromString(args));
    }

    public static UserEditEvent toUserEditEvent(String args) {
        int NUMBER_OF_USER_FIELDS = 1;
        if (args == null) {
            throw new RuntimeException("Для редактирования пользователя надо ввести его id и данные в виде: id имя");
        }
        String[] idAndFields = args.split(" ");
        String[] updatedFields = args.substring(idAndFields[0].length()).trim().split(",");
        if (updatedFields.length != NUMBER_OF_USER_FIELDS) {
            throw new RuntimeException("Неправильное количество введенных для обновления полей.");
        }
        String name = null;
        if (!updatedFields[0].equals(".")) {
            name = updatedFields[0];
        }
        return new UserEditEvent(getIdFromString(idAndFields[0]), name);
    }

    public static UserViewEvent toUserViewEvent(String args) {
        Long userId = null;
        if (args != null) userId = getIdFromString(args);
        return new UserViewEvent(userId);
    }

    public static UserViewWithMaxNumberTasksEvent toUserViewWithMaxNumberTasksEvent(String args) {
        UserFilter userFilter = new UserFilter();
        if (args != null) {
            fillUserFilter(args, userFilter);
        }
        return new UserViewWithMaxNumberTasksEvent(userFilter);
    }

    public static UserViewTaskEvent toUserViewTaskEvent(String args) {
        if (args == null) {
            throw new RuntimeException("Для просмотра задач пользователя надо ввести его id и фильтры (опционально)");
        }
        String[] userIdAndFilters = args.split(" ");
        UserFilter userFilter = new UserFilter();
        if (userIdAndFilters.length > 1) {
            fillUserFilter(args.substring(userIdAndFilters[0].length()).trim(), userFilter);
        }
        return new UserViewTaskEvent(getIdFromString(userIdAndFilters[0]), userFilter);
    }

    private static void fillUserFilter(String args, UserFilter userFilter) {
        String[] filters = args.split("<>");
        for (String filter : filters) {
            String[] filterAndArg = filter.split("=");
            if (filterAndArg.length > 1) {
                String concreteFilter = filterAndArg[0];
                String filterArg = filterAndArg[1];
                userFilter.setFilter(concreteFilter, filterArg);
            }
        }
    }

}
