package application.model;

import java.time.LocalDate;

public class Task {
    private final String id;
    private final String title;
    private final String description;
    private final String userId;
    private final LocalDate date;
    private Status status;

    public enum Status {
        CREATED("Создана"),
        RUN("В работе"),
        COMPLETED("Выполнена");

        private final String description;

        Status(String description) {
            this.description = description;
        }
    }

    public Task(String id, String title, String description, String userId, LocalDate date) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.status = Status.CREATED;
    }


    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public Status getStatus() {
        return status;
    }

    public void changeStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "   Id: " + id + "\n" +
                "   Заголовок: " + title + "\n" +
                "   Описание: " + description + "\n" +
                "   Дедлайн: " + date + "\n" +
                "   Статус: " + status.description;
    }
}
