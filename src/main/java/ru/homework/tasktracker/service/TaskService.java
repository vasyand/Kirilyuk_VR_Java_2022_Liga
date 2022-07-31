package ru.homework.tasktracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.homework.tasktracker.model.dto.TaskFullDto;
import ru.homework.tasktracker.model.dto.TaskPostDto;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.filter.TaskFilter;

import java.util.List;

public interface TaskService {
    TaskFullDto findById(Long id);

    Page<TaskFullDto> findAll(TaskFilter taskFilter, Pageable pageable);

    void delete(Long id);

    Long save(TaskPostDto taskPostDto);

    void update(TaskPostDto taskPostDto, Long id);
}
