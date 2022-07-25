package ru.homework.tasktracker.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    private Long id;
    private String message;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

}
