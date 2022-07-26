package ru.homework.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.filter.TaskFilter;
import ru.homework.tasktracker.repository.TaskRepository;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.specification.TaskSpecification;

import java.util.List;

import static ru.homework.tasktracker.specification.TaskSpecification.*;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Задачи с id %s не существует", id)));
    }

    @Override
    public List<Task> findAll(TaskFilter taskFilter) {
        return taskRepository.findAll(generateSpecificationByTaskFilter(taskFilter));
    }

    @Override
    public void delete(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
    }

    @Override
    public void save(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void update(Task task) {
        taskRepository.save(task);

    }
}
