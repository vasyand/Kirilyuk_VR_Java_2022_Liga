package ru.homework.tasktracker.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projects")
public class Project {
    @Id
    private Long id;
    private String title;
    private String description;

    @OneToMany(mappedBy = "project",
            cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<Task> tasks;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    private List<User> users;

    public Project(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
