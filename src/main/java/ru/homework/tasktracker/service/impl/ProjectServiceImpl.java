package ru.homework.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.homework.tasktracker.mapper.ProjectMapper;
import ru.homework.tasktracker.model.dto.ProjectFullDto;
import ru.homework.tasktracker.model.dto.ProjectPostDto;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.model.filter.ProjectFilter;
import ru.homework.tasktracker.repository.ProjectRepository;
import ru.homework.tasktracker.service.ProjectService;

import static ru.homework.tasktracker.mapper.ProjectMapper.*;
import static ru.homework.tasktracker.specification.ProjectSpecification.generateSpecificationByProjectFilter;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    public ProjectFullDto findById(Long id) {
        Project project = this.findProjectById(id);
        return projectToProjectFullDto(project);
    }

    @Override
    public Page<ProjectFullDto> findAll(ProjectFilter projectFilter, Pageable pageable) {
        Page<Project> projects = projectRepository.findAll(generateSpecificationByProjectFilter(projectFilter), pageable);
        return projects.map(ProjectMapper::projectToProjectFullDto);
    }

    @Override
    public Long save(ProjectPostDto projectPostDto) {
       Project project = projectPostDtoToProject(projectPostDto);
       return projectRepository.save(project).getId();
    }

    @Override
    public void delete(Long id) {
        Project project = this.findProjectById(id);
        projectRepository.delete(project);
    }

    @Override
    public void update(ProjectPostDto projectPostDto, Long id) {
        Project project = this.findProjectById(id);
        projectPostDtoMergeWithProject(projectPostDto, project);
        projectRepository.save(project);
    }

    private Project findProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Проекта с id %s не существует", id)));
    }
}
