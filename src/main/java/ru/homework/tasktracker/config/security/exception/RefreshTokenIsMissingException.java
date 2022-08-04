package ru.homework.tasktracker.config.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RefreshTokenIsMissingException extends RuntimeException{
    public RefreshTokenIsMissingException() {
        super("Refresh token is missing");
    }
}
