package ru.homework.tasktracker.service;

import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.filter.TaskFilter;

import java.util.List;

public interface TaskService {
    Task findById(Long id);

    List<Task> findAll(TaskFilter taskFilter);

    void delete(Long id);

    void save(Task task);

    void update(Task task);
}
