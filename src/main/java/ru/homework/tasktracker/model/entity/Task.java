package ru.homework.tasktracker.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    public Task(String title, String description, User user, LocalDate date, TaskStatus taskStatus) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.date = date;
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return "   Id: " + id + "\n" +
                "   Пользователь: " + user + "\n" +
                "   Заголовок: " + title + "\n" +
                "   Описание: " + description + "\n" +
                "   Дедлайн: " + date + "\n" +
                "   Статус: " + taskStatus.getDescription();
    }
}
