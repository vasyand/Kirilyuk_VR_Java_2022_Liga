package application.repository;


import application.model.Task;

import java.util.List;

public interface TaskRepository {

    List<Task> findAll();

    Task findById(int id);

    void delete(int id);

    void save(Task task);

    void update(Task task);

    List<Task> findByUserId(int userId);

    void deleteAll();
}
