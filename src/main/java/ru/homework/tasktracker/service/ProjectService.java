package ru.homework.tasktracker.service;

import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.model.filter.ProjectFilter;

import java.util.List;

public interface ProjectService {
    Project findById(Long id);
    List<Project> findAll(ProjectFilter projectFilter);

    void save(Project project);

    void delete(Long id);

    void update(Project project);
}
