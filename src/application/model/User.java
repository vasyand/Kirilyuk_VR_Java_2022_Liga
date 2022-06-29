package application.model;

import java.util.List;

public class User {
    private int id;
    private String name;
    private List<Task> tasks;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "   " + name + " с id - " + id;
    }
}
