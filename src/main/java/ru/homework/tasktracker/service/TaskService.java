package ru.homework.tasktracker.service;

import ru.homework.tasktracker.model.entity.Task;

import java.util.List;

public interface TaskService {
    Task findById(Long id);

    List<Task> findAll();

    void delete(Long id);

    void save(Task task);

    void update(Task task);
}
