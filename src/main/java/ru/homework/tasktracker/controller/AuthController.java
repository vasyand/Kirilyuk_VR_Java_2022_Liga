package ru.homework.tasktracker.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/login/success")
    public ResponseEntity<?> successLogin() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/logout/success")
    public ResponseEntity<?> successLogout() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
