package ru.homework.tasktracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.homework.tasktracker.model.dto.ProjectFullDto;
import ru.homework.tasktracker.model.dto.ProjectPostDto;
import ru.homework.tasktracker.model.filter.ProjectFilter;

public interface ProjectService {
    ProjectFullDto findById(Long id);
    Page<ProjectFullDto> findAll(ProjectFilter projectFilter, Pageable pageable);

    Long save(ProjectPostDto project);

    void delete(Long id);

    void update(ProjectPostDto project, Long id);
}
