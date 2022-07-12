package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.TaskEvent;
import ru.homework.tasktracker.model.TaskStrategyName;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.entity.TaskStatus;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.service.UserService;
import ru.homework.tasktracker.strategy.TaskStrategy;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static ru.homework.tasktracker.model.StrategyResponse.*;

@Component
@RequiredArgsConstructor
public class TaskEditStrategy implements TaskStrategy {
    private static final int NUMBER_OF_TASK_FIELDS = 5;
    private final TaskService taskService;
    private final UserService userService;


    @Override
    public StrategyResponse execute(TaskEvent event) {
        try {
            if (event.getArgs() == null) {
                throw new RuntimeException("Для редактирования задачи надо ввести его id и данные в виде: " +
                        "id заголовок,описание,id пользователя,дата,статус");
            }
            String[] args = event.getArgs().split(" ");
            String taskId = args[0];
            Task task = taskService.findById(Long.valueOf(taskId));
            if (needToUpdate(args)) {
                String updatedFields = event.getArgs().substring(taskId.length()).trim();
                merge(task, updatedFields);
            }
            taskService.update(task);
            return new StrategyResponse("Задача успешно обновлена!", Status.OK);
        } catch (NumberFormatException | DateTimeException e) {
            return new StrategyResponse("Неверно введена дата или id пользователя/задачи", Status.BAD);
        } catch (RuntimeException e) {
            return new StrategyResponse(e.getMessage(), Status.BAD);
        }
    }

    private boolean needToUpdate(String[] args) {
        return args.length > 1;
    }

    private void merge(Task task, String fields) {
        String[] args = fields.split(",");
        if (args.length != NUMBER_OF_TASK_FIELDS) {
            throw new RuntimeException("Неправильное количество введенных для обновления полей." +
                    "Если какие-то поля не обновляются, их надо заменить точками, напирмер: .,.,.,.,RUN");
        }
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

    @Override
    public TaskStrategyName getStrategyName() {
        return TaskStrategyName.EDIT;
    }
}
