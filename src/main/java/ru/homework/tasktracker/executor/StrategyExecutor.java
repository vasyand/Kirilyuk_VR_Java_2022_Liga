package ru.homework.tasktracker.executor;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.*;
import ru.homework.tasktracker.strategy.argument.Argument;
import ru.homework.tasktracker.strategy.Strategy;
import ru.homework.tasktracker.util.ArgumentHelperUtil;

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

    public StrategyResponse executeStrategy(String eventString) {
        if (!ArgumentHelperUtil.isValidEvent(eventString)) {
            throw new RuntimeException("Событие невалидное");
        }
        StrategyResponse strategyResponse = null;
        if (ArgumentHelperUtil.isUserStrategy(eventString)) {
            Argument argument = new Argument(eventString);
            strategyResponse = strategiesByType.get(argument.getStrategyName()).execute(argument.getArgs());
        }
        if (strategyResponse == null) {
            throw new RuntimeException("Команда введена неверно");
        }
        return strategyResponse;
    }

    public StrategyResponse executeStrategy(StrategyName strategyName, String args) {
        return strategiesByType.get(strategyName).execute(args);
    }

}
