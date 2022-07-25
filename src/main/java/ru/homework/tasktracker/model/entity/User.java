package ru.homework.tasktracker.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<Task> tasks;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "   " + name + " с id - " + id;
    }
}
