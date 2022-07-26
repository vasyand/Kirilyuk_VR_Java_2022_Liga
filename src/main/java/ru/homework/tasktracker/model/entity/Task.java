package ru.homework.tasktracker.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.homework.tasktracker.util.MessageHelperUtil;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "task")
    @Fetch(FetchMode.JOIN)
    private List<Comment> comments;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    public Task(String title, String description,
                User user, Project project, LocalDate date,
                TaskStatus taskStatus) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.project = project;
        this.date = date;
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return "   Id: " + id + "\n" +
                "   Пользователь: " + user.getId() + "\n" +
                "   Проект: " + project.getId() + "\n" +
                "   Заголовок: " + title + "\n" +
                "   Описание: " + description + "\n" +
                "   Дедлайн: " + date + "\n" +
                "   Статус: " + taskStatus.getDescription() +
                MessageHelperUtil.createMessageFromListOfEntities(
                        "Комментарии: ",
                        "Комментариев к задаче нет",
                        comments);
    }
}
