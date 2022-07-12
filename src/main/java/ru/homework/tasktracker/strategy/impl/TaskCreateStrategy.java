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

import static ru.homework.tasktracker.model.StrategyResponse.Status;

@Component
@RequiredArgsConstructor
public class TaskCreateStrategy implements TaskStrategy {
    private static final int NUMBER_OF_TASK_FIELDS = 4;
    private final TaskService taskService;
    private final UserService userService;

    @Override
    public StrategyResponse execute(TaskEvent event) {
        try {
            if (event.getArgs() == null) {
                throw new RuntimeException("Для создания задачи надо ввести ее данные в виде: " +
                        "id,заголовок,описание,id пользователя,дата");
            }
            String[] taskFields = event.getArgs().split(",");
            if (taskFields.length != NUMBER_OF_TASK_FIELDS) {
                throw new RuntimeException("Неверное количество полей для создания задачи");
            }
            User userFoTask = userService.findById(Long.valueOf(taskFields[2]));
            taskService.save(new Task(taskFields[0],
                    taskFields[1],
                    userFoTask,
                    LocalDate.parse(taskFields[3], DateTimeFormatter.ofPattern("d.M.y")),
                    TaskStatus.CREATED));
            return new StrategyResponse("Задача успешно сохранена!", Status.OK);
        } catch (NumberFormatException | DateTimeException e) {
            return new StrategyResponse("Неверно введена дата или id пользователя", Status.BAD);
        } catch (RuntimeException e) {
            return new StrategyResponse(e.getMessage(), Status.BAD);
        }
    }

    @Override
    public TaskStrategyName getStrategyName() {
        return TaskStrategyName.CREATE;
    }
}
