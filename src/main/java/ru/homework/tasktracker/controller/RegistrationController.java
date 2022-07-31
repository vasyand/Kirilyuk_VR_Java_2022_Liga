package ru.homework.tasktracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/registration")
public class RegistrationController {

    @PostMapping
    public ResponseEntity<Long> registerUser() {
        return new ResponseEntity<>(1L, HttpStatus.OK);
    }
}
