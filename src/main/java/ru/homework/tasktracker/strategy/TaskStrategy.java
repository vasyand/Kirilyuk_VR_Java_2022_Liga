package ru.homework.tasktracker.strategy;

import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.event.TaskEvent;
import ru.homework.tasktracker.model.TaskStrategyName;

public interface TaskStrategy {
    StrategyResponse execute(TaskEvent taskEvent);

    TaskStrategyName getStrategyName();
}
