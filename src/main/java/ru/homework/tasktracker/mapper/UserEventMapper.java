package ru.homework.tasktracker.mapper;

import ru.homework.tasktracker.model.event.*;
import ru.homework.tasktracker.model.filter.UserFilter;

import static ru.homework.tasktracker.util.StringParserUtil.getIdFromString;


public class UserEventMapper {

    public static UserCreateEvent toUserCreateEvent(String args) {
        int NUMBER_OF_USER_FIELDS = 5;
        if (args == null) {
            throw new RuntimeException("Для создания задачи надо ввести ее данные в виде: " +
                    "id,заголовок,описание,id пользователя,дата");
        }
        String[] userFields = args.split(",");
        if (userFields.length != NUMBER_OF_USER_FIELDS) {
            throw new RuntimeException("Неверное количество полей для создания пользователя");
        }
        return new UserCreateEvent(userFields[0],
                userFields[1],
                userFields[2],
                userFields[3],
                userFields[4]);
    }

    public static UserDeleteEvent toUserDeleteEvent(String args) {
        if (args == null) {
            throw new RuntimeException("Для удаления пользователя псоле команды надо ввести его id");
        }
        return new UserDeleteEvent(getIdFromString(args));
    }

    public static UserEditEvent toUserEditEvent(String args) {
        int NUMBER_OF_USER_FIELDS = 3;
        if (args == null) {
            throw new RuntimeException("Для редактирования пользователя надо ввести его id и данные в виде: id имя");
        }
        String[] idAndFields = args.split(" ");
        String[] updatedFields = args.substring(idAndFields[0].length()).trim().split(",");
        if (updatedFields.length != NUMBER_OF_USER_FIELDS) {
            throw new RuntimeException("Неправильное количество введенных для обновления полей.");
        }
        String firstName = null;
        String middleName = null;
        String lastName = null;
        if (!updatedFields[0].equals(".")) {
            firstName = updatedFields[0];
        }
        if (!updatedFields[1].equals(".")) {
            middleName = updatedFields[1];
        }
        if (!updatedFields[2].equals(".")) {
            lastName = updatedFields[2];
        }
        return new UserEditEvent(getIdFromString(idAndFields[0]), firstName, middleName, lastName);
    }

    public static UserViewEvent toUserViewEvent(String args) {
        if (args == null) {
            throw new RuntimeException("Для просмотра пользователя псоле команды надо ввести его id");
        }
        return new UserViewEvent(getIdFromString(args));
    }

    public static UserViewAllEvent toUserViewAllEvent(String args) {
        UserFilter userFilter = new UserFilter();
        if (args != null) {
            fillUserFilter(args, userFilter);
        }
        return new UserViewAllEvent(userFilter);
    }

    public static UserViewWithMaxNumberTasksEvent toUserViewWithMaxNumberTasksEvent(String args) {
        UserFilter userFilter = new UserFilter();
        if (args != null) {
            fillUserFilter(args, userFilter);
        }
        return new UserViewWithMaxNumberTasksEvent(userFilter);
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
