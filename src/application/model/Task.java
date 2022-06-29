package application.model;

import java.time.LocalDate;

public class Task {
    private int id;
    private String title;
    private String description;
    private int userId;
    private LocalDate date;
    private TaskStatus taskStatus;

    public Task(int id, String title, String description, int userId, LocalDate date, TaskStatus taskStatus) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.date = date;
        this.taskStatus = taskStatus;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return "   Id: " + id + "\n" +
                "   Id пользователя: " + userId + "\n" +
                "   Заголовок: " + title + "\n" +
                "   Описание: " + description + "\n" +
                "   Дедлайн: " + date + "\n" +
                "   Статус: " + taskStatus.getDescription();
    }
}
