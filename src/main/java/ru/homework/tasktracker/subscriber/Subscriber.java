package ru.homework.tasktracker.subscriber;

import ru.homework.tasktracker.model.Event;

public interface Subscriber {
    void execute(Event event);
}
