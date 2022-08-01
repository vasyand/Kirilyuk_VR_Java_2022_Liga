package ru.homework.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.homework.tasktracker.mapper.TaskMapper;
import ru.homework.tasktracker.model.dto.TaskFullDto;
import ru.homework.tasktracker.model.dto.TaskPostDto;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.filter.TaskFilter;
import ru.homework.tasktracker.repository.TaskRepository;
import ru.homework.tasktracker.service.ProjectService;
import ru.homework.tasktracker.service.TaskService;
import ru.homework.tasktracker.service.UserService;

import static ru.homework.tasktracker.mapper.TaskMapper.*;
import static ru.homework.tasktracker.specification.TaskSpecification.generateSpecificationByTaskFilter;
import static ru.homework.tasktracker.specification.UserSpecification.generateSpecificationByUserFilter;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ProjectService projectService;

    @Override
    public TaskFullDto findById(Long id) {
        Task task = findTaskById(id);
        return taskToTaskFullDto(task);
    }

    @Override
    public Page<TaskFullDto> findAll(TaskFilter taskFilter, Pageable pageable) {
        Page<Task> tasks;
        if (taskFilter != null) {
            tasks = taskRepository.findAll(generateSpecificationByTaskFilter(taskFilter), pageable);
        } else {
            tasks = taskRepository.findAll(pageable);
        }
        return tasks.map(TaskMapper::taskToTaskFullDto);
    }

    @Override
    public void delete(Long id) {
        Task task = this.findTaskById(id);
        taskRepository.delete(task);
    }

    @Override
    public Long save(TaskPostDto taskPostDto) {
        userService.findById(taskPostDto.getUserId());
        projectService.findById(taskPostDto.getProjectId());
        Task task = taskPostDtoToTask(taskPostDto);
        return taskRepository.save(task).getId();
    }

    @Override
    public void update(TaskPostDto taskPostDto, Long id) {
        Task task = this.findTaskById(id);
        taskPostDtoMergeWithTask(taskPostDto, task);
        userService.findById(task.getUser().getId());
        projectService.findById(task.getProject().getId());
        taskRepository.save(task);
    }

    private Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Задачи с id %s не существует", id)));
    }
}
