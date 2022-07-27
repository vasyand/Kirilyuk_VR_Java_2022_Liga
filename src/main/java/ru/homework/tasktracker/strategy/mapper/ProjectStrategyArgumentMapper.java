package ru.homework.tasktracker.strategy.mapper;

import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.model.filter.CommentFilter;
import ru.homework.tasktracker.model.filter.ProjectFilter;
import ru.homework.tasktracker.strategy.argument.*;

import static ru.homework.tasktracker.util.StringParserUtil.getIdFromString;

public class ProjectStrategyArgumentMapper {

    public static ProjectCreateArgument toProjectCreateArgument(String args) {
        int NUMBER_OF_PROJECT_FIELDS = 2;
        if (args == null) {
            throw new RuntimeException("Для создания проекта надо ввести его данные в виде: " +
                    "заголовок,описание");
        }
        String[] projectFields = args.split(",");
        if (projectFields.length != NUMBER_OF_PROJECT_FIELDS) {
            throw new RuntimeException("Неверное количество полей для создания проекта");
        }
        return new ProjectCreateArgument(projectFields[0], projectFields[1]);
    }

    public static ProjectDeleteArgument toProjectDeleteArgument(String arg) {
        if (arg == null) {
            throw new RuntimeException("Для удаления проекта после команды надо ввести его id");
        }
        return new ProjectDeleteArgument(getIdFromString(arg));
    }

    public static ProjectEditArgument toProjectEditArgument(String args) {
        int NUMBER_OF_PROJECT_FIELDS = 2;
        if (args == null) {
            throw new RuntimeException(
                    "Для редактирования проекта надо ввести его id и данные в виде: id заголовок, описание");
        }
        String[] idAndFields = args.split(" ");
        String[] updatedFields = args.substring(idAndFields[0].length()).trim().split(",");
        if (updatedFields.length != NUMBER_OF_PROJECT_FIELDS) {
            throw new RuntimeException("Неправильное количество введенных для обновления полей.");
        }
        String title = null;
        String description = null;
        if (!updatedFields[0].equals(".")) {
            title = updatedFields[0];
        }
        if (!updatedFields[1].equals(".")) {
            description = updatedFields[1];
        }
        return new ProjectEditArgument(getIdFromString(idAndFields[0]), title, description);
    }

    public static ProjectViewArgument toProjectViewArgument(String args) {
        Long projectId = null;
        if (args != null) projectId = getIdFromString(args);
        return new ProjectViewArgument(projectId);
    }

    public static ProjectViewAllArgument toProjectViewAllArgument(String args) {
        ProjectFilter projectFilter = new ProjectFilter();
        if (args != null) {
            fillProjectFilter(args, projectFilter);
        }
        return new ProjectViewAllArgument(projectFilter);
    }

    private static void fillProjectFilter(String args, ProjectFilter projectFilter) {
        String[] filters = args.split("<>");
        for (String filter : filters) {
            String[] filterAndArg = filter.split("=");
            if (filterAndArg.length > 1) {
                String concreteFilter = filterAndArg[0];
                String filterArg = filterAndArg[1];
                projectFilter.setFilter(concreteFilter, filterArg);
            }
        }
    }
}
