package ru.homework.tasktracker.model.event;

import lombok.Getter;
import ru.homework.tasktracker.model.TaskStrategyName;

@Getter
public class TaskEvent {
    private TaskStrategyName taskStrategyName;
    private String args;

    public TaskEvent(String eventString) {
        String[] eventArgs = eventString.split(" ");
        if (eventArgs.length > 1) {
            taskStrategyName = TaskStrategyName.valueOf(eventArgs[1].toUpperCase());
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
