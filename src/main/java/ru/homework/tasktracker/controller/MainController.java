package ru.homework.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.homework.tasktracker.publisher.EventPublisher;
import ru.homework.tasktracker.model.Event;
import ru.homework.tasktracker.validation.EventValidator;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final EventPublisher eventPublisher;
    private final EventValidator eventValidator;


    @GetMapping
    public ResponseEntity<?> executeCommand(@RequestParam("event") String eventString) {
        Event event = new Event(eventString);
        if (eventValidator.isValidEvent(event)) {
            eventPublisher.publishEvent(event);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
