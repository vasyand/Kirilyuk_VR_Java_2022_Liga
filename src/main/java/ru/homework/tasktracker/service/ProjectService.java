package ru.homework.tasktracker.service;

import ru.homework.tasktracker.model.entity.Project;

public interface ProjectService {
    Project findById(Long id);
}
