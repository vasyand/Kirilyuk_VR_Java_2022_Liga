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

@Component("task-create")
@RequiredArgsConstructor
public class TaskCreateSubscriber implements TaskSubscriber {
    private final TaskService taskService;
    private final UserService userService;

    @Override
    public void execute(Event event) {
        if (event.getArgs() == null) {
            throw new RuntimeException("Для создания задачи надо ввести ее данные в виде: " +
                    "id,заголовок,описание,id пользователя,дата");
        }
        String[] taskFields = event.getArgs().split(",");
        if (taskFields.length != 4) {
            throw new RuntimeException("Неверное количество полей для создания задачи");
        }
        User userFoTask = userService.findById(Long.valueOf(taskFields[2]));
        taskService.save(new Task(taskFields[0],
                taskFields[1],
                userFoTask,
                LocalDate.parse(taskFields[3], DateTimeFormatter.ofPattern("d.M.y")),
                TaskStatus.CREATED));
        System.out.println("Задача создана");
    }
}
