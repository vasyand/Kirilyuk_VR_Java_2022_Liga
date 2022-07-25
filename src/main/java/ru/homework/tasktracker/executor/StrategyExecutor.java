package ru.homework.tasktracker.executor;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.*;
import ru.homework.tasktracker.model.event.Event;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.util.EventHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class StrategyExecutor {
    private final Map<StrategyName, Strategy> strategiesByType = new HashMap<>();

    @Autowired
    public StrategyExecutor(Set<Strategy> strategies) {
        strategies.forEach(s -> strategiesByType.put(s.getStrategyName(), s));
    }

    public StrategyResponse executeEvent(String eventString) {
        if (!EventHelper.isValidEvent(eventString)) {
            throw new RuntimeException("Событие невалидное");
        }
        return executeStrategy(eventString);
    }

    private StrategyResponse executeStrategy(String eventString) {
        StrategyResponse strategyResponse = null;
        if (EventHelper.isUserStrategy(eventString)) {
            Event event = new Event(eventString);
            strategyResponse = strategiesByType.get(event.getStrategyName()).execute(event.getArgs());
        }
        if (strategyResponse == null) {
            throw new RuntimeException("Команда введена неверно");
        }
        return strategyResponse;
    }
}
