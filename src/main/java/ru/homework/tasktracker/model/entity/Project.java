package ru.homework.tasktracker.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String title;
    private String description;

    @OneToMany(mappedBy = "project",
            cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<Task> tasks;

    @ManyToMany(mappedBy = "projects")
    private List<User> users = new ArrayList<>();
}
