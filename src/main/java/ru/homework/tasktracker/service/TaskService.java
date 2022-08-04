package ru.homework.tasktracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.homework.tasktracker.model.dto.TaskFullDto;
import ru.homework.tasktracker.model.dto.TaskCreateDto;
import ru.homework.tasktracker.model.dto.TaskUpdateDto;
import ru.homework.tasktracker.model.filter.TaskFilter;

public interface TaskService {
    TaskFullDto findById(Long id);

    Page<TaskFullDto> findAll(TaskFilter taskFilter, Pageable pageable);

    void delete(Long id);

    TaskFullDto save(TaskCreateDto taskCreateDto);

    TaskFullDto update(TaskUpdateDto taskUpdateDto, Long id);
}
