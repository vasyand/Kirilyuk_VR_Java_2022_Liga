package ru.homework.tasktracker.publisher;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.Event;
import ru.homework.tasktracker.subscriber.Subscriber;
import ru.homework.tasktracker.subscriber.TaskSubscriber;
import ru.homework.tasktracker.subscriber.UserSubscriber;
import ru.homework.tasktracker.util.EventValidator;

import java.time.format.DateTimeParseException;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventPublisher {
    private final Map<String, UserSubscriber> userEventSubscribers;
    private final Map<String, TaskSubscriber> taskEventSubscribers;

    public void publishEvent(String eventString) {
        Event event = new Event(eventString);
        if (EventValidator.isValidEvent(event)) {
            String prefix = event.getType().equals("user") ? "user-" : "task-";
            try {
                Subscriber subscriber;
                if (event.getType().equals("user")) {
                    subscriber = userEventSubscribers.get(prefix + event.getCommand());
                } else {
                    subscriber = taskEventSubscribers.get(prefix + event.getCommand());
                }
                if (subscriber == null) {
                    throw new RuntimeException("Такой команды не существует!");
                }
                subscriber.execute(event);
            } catch (DateTimeParseException ex) {
                log.error("Дата должна быть в формате 12.01.2022");
            } catch (NumberFormatException ex) {
                log.error("Id должен быть числовым!");
            } catch (RuntimeException ex) {
                log.error(ex.getMessage());
            }
        }
    }
}
