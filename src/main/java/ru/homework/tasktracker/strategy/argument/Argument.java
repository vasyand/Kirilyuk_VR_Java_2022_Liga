package ru.homework.tasktracker.strategy.argument;

import lombok.Getter;
import ru.homework.tasktracker.model.StrategyName;

@Getter
public class Argument {
    private StrategyName strategyName;
    private String args;

    public Argument(String eventString) {
        String[] eventArgs = eventString.split(" ");
        if (eventArgs.length > 1) {
            strategyName = StrategyName.valueOf(eventArgs[1].toUpperCase());
        }
        if (eventArgs.length > 2) {
            args = "";
            for (int i = 2; i < eventArgs.length; i++) {
                args += eventArgs[i] + " ";
            }
            args = args.trim();
        }
    }
}
