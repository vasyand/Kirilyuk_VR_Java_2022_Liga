package ru.homework.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.homework.tasktracker.exception.EntityNotFoundException;
import ru.homework.tasktracker.mapper.ProjectMapper;
import ru.homework.tasktracker.model.dto.ProjectCreateDto;
import ru.homework.tasktracker.model.dto.ProjectFullDto;
import ru.homework.tasktracker.model.dto.ProjectUpdateDto;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.model.filter.ProjectFilter;
import ru.homework.tasktracker.repository.ProjectRepository;
import ru.homework.tasktracker.service.ProjectService;

import javax.transaction.Transactional;

import static java.lang.String.format;
import static ru.homework.tasktracker.specification.ProjectSpecification.generateSpecificationByProjectFilter;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectFullDto findById(Long id) {
        Project project = this.findProjectById(id);
        return projectMapper.projectToProjectFullDto(project);
    }

    @Override
    public Page<ProjectFullDto> findAll(ProjectFilter projectFilter, Pageable pageable) {
        Page<Project> projects = projectRepository.findAll(generateSpecificationByProjectFilter(projectFilter), pageable);
        return projects.map(projectMapper::projectToProjectFullDto);
    }

    @Override
    public ProjectFullDto save(ProjectCreateDto projectCreateDto) {
       Project project = projectMapper.projectPostDtoToProject(projectCreateDto);
       return projectMapper.projectToProjectFullDto(projectRepository.save(project));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Project project = this.findProjectById(id);
        projectRepository.delete(project);
    }

    @Override
    @Transactional
    public ProjectFullDto update(ProjectUpdateDto projectUpdateDto, Long id) {
        Project project = this.findProjectById(id);
        projectMapper.projectUpdateDtoMergeWithProject(projectUpdateDto, project);
        return projectMapper.projectToProjectFullDto(projectRepository.save(project));
    }

    private Project findProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Проекта с id %s не существует", id)));
    }
}
