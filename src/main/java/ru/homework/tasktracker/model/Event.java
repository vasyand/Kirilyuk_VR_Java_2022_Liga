package ru.homework.tasktracker.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event {
    private String type;
    private String command;
    private String args;

    public Event(String eventString) {
        String[] eventArgs = eventString.split(" ");
        if (eventArgs.length > 0) {
            type = eventArgs[0];
        }
        if (eventArgs.length > 1) {
            command = eventArgs[1];
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
