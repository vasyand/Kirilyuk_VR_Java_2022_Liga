package ru.homework.tasktracker.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.homework.tasktracker.util.MessageHelperUtil;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    @Fetch(FetchMode.JOIN)
    private List<Task> tasks;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    private List<Project> projects;

    public User(String firstName, String middleName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s с id - %s\n", lastName, firstName, middleName, id)
                + MessageHelperUtil.createMessageFromListOfEntities(
                "Его задачи: ",
                "У него нет задач",
                tasks)
                + MessageHelperUtil.createMessageFromListOfEntities(
                "Проекты, в которых он участвует",
                "Он не участвует в проектах",
                projects
        );
    }
}
