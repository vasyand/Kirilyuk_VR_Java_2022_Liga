package ru.homework.tasktracker.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.TaskEvent;
import ru.homework.tasktracker.model.TaskStrategyName;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.strategy.TaskStrategy;

import static ru.homework.tasktracker.model.StrategyResponse.*;

@Component
@RequiredArgsConstructor
public class TaskDeleteStrategy implements TaskStrategy {
    private final TaskService taskService;

    @Override
    public StrategyResponse execute(TaskEvent event) {
        try {
            String taskId = event.getArgs();
            if (taskId == null) {
                throw new RuntimeException("Для удаления задачи после команды надо ввести его id");
            }
            taskService.delete(Long.valueOf(taskId));
            return new StrategyResponse("Задача успешно удалена!", Status.OK);
        } catch (NumberFormatException e) {
            return new StrategyResponse("id должен быть числовым значением", Status.BAD);
        } catch (RuntimeException e) {
           return new StrategyResponse(e.getMessage(), Status.BAD);
        }
    }

    @Override
    public TaskStrategyName getStrategyName() {
        return TaskStrategyName.DELETE;
    }
}
