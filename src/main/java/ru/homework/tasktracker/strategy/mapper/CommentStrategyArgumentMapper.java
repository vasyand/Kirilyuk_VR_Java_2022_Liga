package ru.homework.tasktracker.strategy.mapper;

import ru.homework.tasktracker.model.filter.CommentFilter;
import ru.homework.tasktracker.strategy.argument.*;

import static ru.homework.tasktracker.util.StringParserUtil.getIdFromString;

public class CommentStrategyArgumentMapper {
    public static CommentCreateArgument toCommentCreateArgument(String args) {
        int NUMBER_OF_COMMENT_FIELDS = 2;
        if (args == null) {
            throw new RuntimeException("Для создания комментария надо ввести его данные в виде: " +
                    "сообщение,id задачи");
        }
        String[] commentFields = args.split(",");
        if (commentFields.length != NUMBER_OF_COMMENT_FIELDS) {
            throw new RuntimeException("Неверное количество полей для создания комментария");
        }
        return new CommentCreateArgument(
                commentFields[0],
                getIdFromString(commentFields[1])
        );
    }

    public static CommentDeleteArgument toCommentDeleteArgument(String arg) {
        if (arg == null) {
            throw new RuntimeException("Для удаления комментария после команды надо ввести его id");
        }
        return new CommentDeleteArgument(getIdFromString(arg));
    }

    public static CommentEditArgument toCommentEditArgument(String args) {
        int NUMBER_OF_COMMENT_FIELDS = 1;
        if (args == null) {
            throw new RuntimeException("Для редактирования комментария надо ввести его id и данные в виде: id сообщение");
        }
        String[] idAndFields = args.split(" ");
        String[] updatedFields = args.substring(idAndFields[0].length()).trim().split(",");
        if (updatedFields.length != NUMBER_OF_COMMENT_FIELDS) {
            throw new RuntimeException("Неправильное количество введенных для обновления полей.");
        }
        String message = null;
        Long taskId = null;
        if (!updatedFields[0].equals(".")) {
            message = updatedFields[0];
        }
        if (!updatedFields[1].equals(".")) {
            taskId = getIdFromString(updatedFields[1]);
        }
        return new CommentEditArgument(getIdFromString(idAndFields[0]), message, taskId);
    }

    public static CommentViewArgument toCommentViewArgument(String args) {
        Long commentId = null;
        if (args != null) commentId = getIdFromString(args);
        return new CommentViewArgument(commentId);
    }

    public static CommentViewAllArgument toCommentViewAllArgument(String args) {
        CommentFilter commentFilter = new CommentFilter();
        if (args != null) {
            fillCommentFilter(args, commentFilter);
        }
        return new CommentViewAllArgument(commentFilter);
    }

    private static void fillCommentFilter(String args, CommentFilter commentFilter) {
        String[] filters = args.split("<>");
        for (String filter : filters) {
            String[] filterAndArg = filter.split("=");
            if (filterAndArg.length > 1) {
                String concreteFilter = filterAndArg[0];
                String filterArg = filterAndArg[1];
                commentFilter.setFilter(concreteFilter, filterArg);
            }
        }
    }
}
