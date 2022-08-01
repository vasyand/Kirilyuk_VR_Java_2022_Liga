package ru.homework.tasktracker.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/login/success")
    public String successLogin() {
        return "Авторизация прошла успешно!";
    }

    @GetMapping("/logout/success")
    public String successLogout() {
        return "Теперь ты разлогинен";
    }

}
