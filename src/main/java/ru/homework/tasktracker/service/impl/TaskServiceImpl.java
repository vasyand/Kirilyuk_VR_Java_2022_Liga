package ru.homework.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.repository.TaskRepository;
import ru.homework.tasktracker.service.TaskService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Задачи с id %s не существует", id)));
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
        log.info("Задача с id {} удалена", id);
    }

    @Override
    public void save(Task task) {
        taskRepository.save(task);
        log.info("Задача с id {} сохранена", task.getId());
    }

    @Override
    public void update(Task task) {
        taskRepository.save(task);
        log.info("Задача с id {} обновлена", task.getId());
    }
}
