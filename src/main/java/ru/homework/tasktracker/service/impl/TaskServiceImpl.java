package ru.homework.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.homework.tasktracker.exception.EntityNotFoundException;
import ru.homework.tasktracker.mapper.TaskMapper;
import ru.homework.tasktracker.model.dto.TaskCreateDto;
import ru.homework.tasktracker.model.dto.TaskFullDto;
import ru.homework.tasktracker.model.dto.TaskUpdateDto;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.filter.TaskFilter;
import ru.homework.tasktracker.repository.TaskRepository;
import ru.homework.tasktracker.service.ProjectService;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.service.UserService;

import javax.transaction.Transactional;

import static java.lang.String.format;
import static ru.homework.tasktracker.specification.TaskSpecification.generateSpecificationByTaskFilter;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ProjectService projectService;

    private final TaskMapper taskMapper;

    @Override
    public TaskFullDto findById(Long id) {
        Task task = findTaskById(id);
        return taskMapper.taskToTaskFullDto(task);
    }

    @Override
    public Page<TaskFullDto> findAll(TaskFilter taskFilter, Pageable pageable) {
        Page<Task> tasks = taskRepository.findAll(generateSpecificationByTaskFilter(taskFilter), pageable);
        return tasks.map(taskMapper::taskToTaskFullDto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Task task = this.findTaskById(id);
        taskRepository.delete(task);
    }

    @Override
    @Transactional
    public TaskFullDto save(TaskCreateDto taskCreateDto) {
        userService.findById(taskCreateDto.getUserId());
        projectService.findById(taskCreateDto.getProjectId());
        Task task = taskMapper.taskCreateDtoToTask(taskCreateDto);
        return taskMapper.taskToTaskFullDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public TaskFullDto update(TaskUpdateDto taskUpdateDto, Long id) {
        Task task = this.findTaskById(id);
        taskMapper.taskUpdateDtoMergeWithTask(taskUpdateDto, task);
        userService.findById(task.getUser().getId());
        projectService.findById(task.getProject().getId());
        return taskMapper.taskToTaskFullDto(taskRepository.save(task));
    }

    private Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Задачи с id %s не существует", id)));
    }
}
