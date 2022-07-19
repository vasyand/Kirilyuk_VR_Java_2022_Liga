package ru.homework.tasktracker.executor;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.*;
import ru.homework.tasktracker.model.event.TaskEvent;
import ru.homework.tasktracker.model.event.UserEvent;
import ru.homework.tasktracker.strategy.TaskStrategy;
import ru.homework.tasktracker.strategy.UserStrategy;
import ru.homework.tasktracker.util.EventHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class StrategyExecutor {
    private final Map<UserStrategyName, UserStrategy> userEventStrategies = new HashMap<>();
    private final Map<TaskStrategyName, TaskStrategy> taskEventStrategies = new HashMap<>();

    @Autowired
    public StrategyExecutor(Set<UserStrategy> userStrategies, Set<TaskStrategy> taskStrategies) {
        userStrategies.forEach(s -> userEventStrategies.put(s.getStrategyName(), s));
        taskStrategies.forEach(s -> taskEventStrategies.put(s.getStrategyName(), s));
    }

    public StrategyResponse executeEvent(String eventString) {
        if (!EventHelper.isValidEvent(eventString)) {
            throw new RuntimeException("Событие невалидное");
        }
        return executeStrategy(eventString);
    }

    private StrategyResponse executeStrategy(String eventString) {
        StrategyResponse strategyResponse;
        if (EventHelper.isUserStrategy(eventString)) {
            UserEvent userEvent = new UserEvent(eventString);
            strategyResponse = userEventStrategies.get(userEvent.getUserStrategyName()).execute(userEvent);
        } else {
            TaskEvent taskEvent = new TaskEvent(eventString);
            strategyResponse = taskEventStrategies.get(taskEvent.getTaskStrategyName()).execute(taskEvent);
        }
        if (strategyResponse == null) {
            throw new RuntimeException("Команда введена неверно");
        }
        return strategyResponse;
    }
}
