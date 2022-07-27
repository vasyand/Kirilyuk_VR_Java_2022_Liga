package ru.homework.tasktracker.strategy.mapper;

import ru.homework.tasktracker.model.filter.UserFilter;
import ru.homework.tasktracker.strategy.argument.*;

import static ru.homework.tasktracker.util.StringParserUtil.getIdFromString;


public class UserStrategyArgumentMapper {

    public static UserCreateArgument toUserCreateArgument(String args) {
        int NUMBER_OF_USER_FIELDS = 5;
        if (args == null) {
            throw new RuntimeException("Для создания задачи надо ввести ее данные в виде: " +
                    "id,заголовок,описание,id пользователя,дата");
        }
        String[] userFields = args.split(",");
        if (userFields.length != NUMBER_OF_USER_FIELDS) {
            throw new RuntimeException("Неверное количество полей для создания пользователя");
        }
        return new UserCreateArgument(userFields[0],
                userFields[1],
                userFields[2],
                userFields[3],
                userFields[4]);
    }

    public static UserDeleteArgument toUserDeleteArgument(String args) {
        if (args == null) {
            throw new RuntimeException("Для удаления пользователя псоле команды надо ввести его id");
        }
        return new UserDeleteArgument(getIdFromString(args));
    }

    public static UserAddProjectArgument toAddProjectArgument(String args) {
        int NUMBER_FIELDS_FOR_ADDING_PROJECT = 2;
        if (args == null) {
            throw new RuntimeException(
                    "Для добавления проекта пользователю после команды надо ввести его id и id проекта");
        }
        String[] userIdAndProjectId = args.split(" ");
        if (userIdAndProjectId.length != NUMBER_FIELDS_FOR_ADDING_PROJECT) {
            throw new RuntimeException("Неправильное количество введенных полей.");
        }
        return new UserAddProjectArgument(getIdFromString(userIdAndProjectId[0]),
                getIdFromString(userIdAndProjectId[1]));
    }

    public static UserEditArgument toUserEditArgument(String args) {
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
        return new UserEditArgument(getIdFromString(idAndFields[0]), firstName, middleName, lastName);
    }

    public static UserViewArgument toUserViewArgument(String args) {
        if (args == null) {
            throw new RuntimeException("Для просмотра пользователя псоле команды надо ввести его id");
        }
        return new UserViewArgument(getIdFromString(args));
    }

    public static UserViewAllArgument toUserViewAllArgument(String args) {
        UserFilter userFilter = new UserFilter();
        if (args != null) {
            fillUserFilter(args, userFilter);
        }
        return new UserViewAllArgument(userFilter);
    }

    public static UserViewWithMaxNumberTasksArgument toUserViewWithMaxNumberTasksArgument(String args) {
        UserFilter userFilter = new UserFilter();
        if (args != null) {
            fillUserFilter(args, userFilter);
        }
        return new UserViewWithMaxNumberTasksArgument(userFilter);
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
