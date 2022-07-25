package ru.homework.tasktracker.strategy;

import ru.homework.tasktracker.model.StrategyName;
import ru.homework.tasktracker.model.StrategyResponse;

public interface Strategy {

    StrategyResponse execute(String argument);

    StrategyName getStrategyName();
}
