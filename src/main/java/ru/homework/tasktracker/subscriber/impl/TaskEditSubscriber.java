package ru.homework.tasktracker.subscriber.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.Event;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.entity.TaskStatus;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.subscriber.TaskSubscriber;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component("task-edit")
@RequiredArgsConstructor
public class TaskEditSubscriber implements TaskSubscriber {
    private static final int MIN_NUMBER_OF_TASK_FIELDS_FOR_UPDATING = 1;
    private final TaskService taskService;
    private final UserService userService;


    @Override
    public void execute(Event event) {
        if (event.getArgs() == null) {
            throw new RuntimeException("Для редактирования задачи надо ввести его id и данные в виде: " +
                    "id заголовок,описание,id пользователя,дата,статус");
        }
        String[] args = event.getArgs().split(" ");
        String taskId = args[0];
        Task task = taskService.findById(Long.valueOf(taskId));
        if (args.length > MIN_NUMBER_OF_TASK_FIELDS_FOR_UPDATING) {
            String updatedFields = event.getArgs().substring(taskId.length()).trim();
            merge(task, updatedFields);
        }
        taskService.update(task);
    }

    private void merge(Task task, String fields) {
        String[] args = fields.split(",");
        if (!args[0].equals(".")) {
            task.setTitle(args[0]);
        }
        if (!args[1].equals(".")) {
            task.setDescription(args[1]);
        }
        if (!args[2].equals(".")) {
            User userForTask = userService.findById(Long.valueOf(args[2]));
            task.setUser(userForTask);
        }
        if (!args[3].equals(".")) {
            task.setDate(LocalDate.parse(args[3], DateTimeFormatter.ofPattern("d.M.y")));
        }
        if (!args[4].equals(".") && TaskStatus.getStatusSet().contains(args[4])) {
            task.setTaskStatus(TaskStatus.valueOf(args[4]));
        }
    }
}
