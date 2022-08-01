package ru.homework.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.homework.tasktracker.model.dto.UserCreateDto;
import ru.homework.tasktracker.service.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/registration")
public class RegistrationController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Long> register(@RequestBody @Valid UserCreateDto userCreateDto) {
        Long id = userService.save(userCreateDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
}
