package ru.homework.tasktracker.mapper;

import ru.homework.tasktracker.model.entity.TaskStatus;
import ru.homework.tasktracker.model.event.*;

import java.time.LocalDate;

import static ru.homework.tasktracker.util.StringParser.getDateFromString;
import static ru.homework.tasktracker.util.StringParser.getIdFromString;

public class TaskEventMapper {

    public static TaskCreateEvent toTaskCreateEvent(String args) {
        int NUMBER_OF_TASK_FIELDS = 5;
        if (args == null) {
            throw new RuntimeException("Для создания задачи надо ввести ее данные в виде: " +
                    "заголовок,описание,id пользователя, id проекта, дата");
        }
        String[] taskFields = args.split(",");
        if (taskFields.length != NUMBER_OF_TASK_FIELDS) {
            throw new RuntimeException("Неверное количество полей для создания задачи");
        }
        return new TaskCreateEvent(
                taskFields[0],
                taskFields[1],
                getIdFromString(taskFields[2]),
                getIdFromString(taskFields[3]),
                getDateFromString(taskFields[4])
        );
    }

    public static TaskDeleteEvent toTaskDeleteEvent(String arg) {
        if (arg == null) {
            throw new RuntimeException("Для удаления задачи после команды надо ввести его id");
        }
        return new TaskDeleteEvent(getIdFromString(arg));
    }

    public static TaskEditEvent toTaskEditEvent(String args) {
        int NUMBER_OF_TASK_FIELDS = 6;
        if (args == null) {
            throw new RuntimeException("Для редактирования задачи надо ввести его id и данные в виде: " +
                    "id заголовок,описание,id пользователя,дата,статус");
        }
        String[] idAndFields = args.split(" ");
        String[] updatedFields = args.substring(idAndFields[0].length()).trim().split(",");
        if (updatedFields.length != NUMBER_OF_TASK_FIELDS) {
            throw new RuntimeException("Неправильное количество введенных для обновления полей." +
                    "Если какие-то поля не обновляются, их надо заменить точками, напирмер: .,.,.,.,.,RUN");
        }

        Long taskId = getIdFromString(idAndFields[0]);
        String title = null;
        String description = null;
        Long userId = null;
        Long projectId = null;
        LocalDate date = null;
        TaskStatus status = null;
        if (!updatedFields[0].equals(".")) {
            title = updatedFields[0];
        }
        if (!updatedFields[1].equals(".")) {
            description = updatedFields[1];
        }
        if (!updatedFields[2].equals(".")) {
            userId = getIdFromString(updatedFields[2]);
        }
        if (!updatedFields[3].equals(".")) {
            projectId = getIdFromString(updatedFields[3]);
        }
        if (!updatedFields[4].equals(".")) {
            date = getDateFromString(updatedFields[4]);
        }
        if (!updatedFields[5].equals(".") && TaskStatus.getStatusSet().contains(updatedFields[4])) {
            status = TaskStatus.valueOf(updatedFields[5]);
        }
        return new TaskEditEvent(taskId, title, description, userId, projectId, date, status);
    }

    public static TaskViewEvent toTaskViewEvent(String args) {
        Long taskId = null;
        if (args != null) taskId = getIdFromString(args);
        return new TaskViewEvent(taskId);
    }


}
