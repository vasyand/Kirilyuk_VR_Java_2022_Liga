package ru.homework.tasktracker.model.event;

import lombok.Getter;
import ru.homework.tasktracker.model.StrategyName;

@Getter
public class Event {
    private StrategyName strategyName;
    private String args;

    public Event(String eventString) {
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
