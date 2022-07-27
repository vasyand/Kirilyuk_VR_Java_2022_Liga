package ru.homework.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.model.filter.ProjectFilter;
import ru.homework.tasktracker.repository.ProjectRepository;
import ru.homework.tasktracker.service.ProjectService;

import java.util.List;

import static ru.homework.tasktracker.specification.ProjectSpecification.generateSpecificationByProjectFilter;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    public Project findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Проекта с id %s не существует", id)));
    }

    @Override
    public List<Project> findAll(ProjectFilter projectFilter) {
        return projectRepository.findAll(generateSpecificationByProjectFilter(projectFilter));
    }

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Override
    public void delete(Long id) {
        Project project = this.findById(id);
        projectRepository.delete(project);
    }

    @Override
    public void update(Project project) {
        this.findById(project.getId());
        projectRepository.save(project);
    }
}
