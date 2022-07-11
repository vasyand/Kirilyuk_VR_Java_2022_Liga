package ru.homework.tasktracker.strategy;

import ru.homework.tasktracker.model.StrategyResponse;
import ru.homework.tasktracker.model.UserEvent;
import ru.homework.tasktracker.model.UserStrategyName;

public interface UserStrategy {

    StrategyResponse execute(UserEvent taskEvent);

    UserStrategyName getStrategyName();
}
