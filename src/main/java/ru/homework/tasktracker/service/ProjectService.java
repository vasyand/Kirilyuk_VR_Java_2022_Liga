package ru.homework.tasktracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.homework.tasktracker.model.dto.ProjectFullDto;
import ru.homework.tasktracker.model.dto.ProjectCreateDto;
import ru.homework.tasktracker.model.dto.ProjectUpdateDto;
import ru.homework.tasktracker.model.dto.TaskFullDto;
import ru.homework.tasktracker.model.filter.ProjectFilter;

public interface ProjectService {
    ProjectFullDto findById(Long id);

    Page<ProjectFullDto> findAll(ProjectFilter projectFilter, Pageable pageable);

    ProjectFullDto save(ProjectCreateDto project);

    void delete(Long id);

    ProjectFullDto update(ProjectUpdateDto project, Long id);
}
