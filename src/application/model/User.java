package application.model;

import java.util.List;

public class User {
    private final String id;
    private final String name;
    private final List<Task> tasks;

    public User(String id, String name, List<Task> tasks) {
        this.id = id;
        this.name = name;
        this.tasks = tasks;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return "   " + name + " с id - " + id;
    }
}
