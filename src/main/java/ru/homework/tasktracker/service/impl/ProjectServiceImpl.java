package ru.homework.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.homework.tasktracker.exception.EntityNotFoundException;
import ru.homework.tasktracker.mapper.ProjectMapper;
import ru.homework.tasktracker.model.dto.ProjectFullDto;
import ru.homework.tasktracker.model.dto.ProjectCreateDto;
import ru.homework.tasktracker.model.dto.ProjectUpdateDto;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.model.filter.ProjectFilter;
import ru.homework.tasktracker.repository.ProjectRepository;
import ru.homework.tasktracker.service.ProjectService;

import javax.transaction.Transactional;

import static java.lang.String.*;
import static ru.homework.tasktracker.mapper.ProjectMapper.*;
import static ru.homework.tasktracker.specification.ProjectSpecification.generateSpecificationByProjectFilter;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public ProjectFullDto findById(Long id) {
        Project project = this.findProjectById(id);
        return projectToProjectFullDto(project);
    }

    @Override
    @Transactional
    public Page<ProjectFullDto> findAll(ProjectFilter projectFilter, Pageable pageable) {
        Page<Project> projects;
        if (projectFilter != null) {
            projects = projectRepository.findAll(generateSpecificationByProjectFilter(projectFilter), pageable);
        } else {
            projects = projectRepository.findAll(pageable);
        }
        return projects.map(ProjectMapper::projectToProjectFullDto);
    }

    @Override
    @Transactional
    public Long save(ProjectCreateDto projectCreateDto) {
       Project project = projectPostDtoToProject(projectCreateDto);
       return projectRepository.save(project).getId();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Project project = this.findProjectById(id);
        projectRepository.delete(project);
    }

    @Override
    @Transactional
    public void update(ProjectUpdateDto projectUpdateDto, Long id) {
        Project project = this.findProjectById(id);
        projectPostDtoMergeWithProject(projectUpdateDto, project);
        projectRepository.save(project);
    }

    private Project findProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Проекта с id %s не существует", id)));
    }
}
