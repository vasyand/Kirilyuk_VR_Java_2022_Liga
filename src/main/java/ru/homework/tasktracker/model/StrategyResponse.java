package ru.homework.tasktracker.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StrategyResponse {
    private String message;
    private Status status;


    public StrategyResponse(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public StrategyResponse(Status status) {
        this.status = status;
    }

    public enum Status {
        OK,
        BAD
    }
}
