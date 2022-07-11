package ru.homework.tasktracker.executor;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.*;
import ru.homework.tasktracker.strategy.TaskStrategy;
import ru.homework.tasktracker.strategy.UserStrategy;
import ru.homework.tasktracker.util.EventHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static ru.homework.tasktracker.model.StrategyResponse.Status;

@Component
@RequiredArgsConstructor
@Slf4j
public class StrategyExecutor {
    private final Map<UserStrategyName, UserStrategy> userEventStrategies = new HashMap<>();
    private final Map<TaskStrategyName, TaskStrategy> taskEventStrategies = new HashMap<>();

    @Autowired
    public StrategyExecutor(Set<UserStrategy> userStrategies, Set<TaskStrategy> taskStrategies) {
        userStrategies.forEach(s -> userEventStrategies.put(s.getStrategyName(), s));
        taskStrategies.forEach(s -> taskEventStrategies.put(s.getStrategyName(), s));
    }

    public void executeEvent(String eventString) {
        if (EventHelper.isValidEvent(eventString)) {
            StrategyResponse strategyResponse = executeStrategy(eventString);
            if (strategyResponse.getStatus() == Status.BAD) {
                log.error(strategyResponse.getMessage());
            } else {
                log.info(strategyResponse.getMessage());
            }
        }
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
            strategyResponse = new StrategyResponse("Команда введена неверно", Status.BAD);
        }
        return strategyResponse;
    }
}
