package ru.homework.tasktracker.mapper;

import ru.homework.tasktracker.model.dto.TaskFullDto;
import ru.homework.tasktracker.model.dto.TaskCreateDto;
import ru.homework.tasktracker.model.dto.TaskUpdateDto;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.entity.TaskStatus;
import ru.homework.tasktracker.model.entity.User;

import java.util.stream.Collectors;

import static ru.homework.tasktracker.model.entity.TaskStatus.*;

public class TaskMapper {
    public static TaskFullDto taskToTaskFullDto(Task task) {
        TaskFullDto taskFullDto = new TaskFullDto();
        taskFullDto.setId(task.getId());
        taskFullDto.setDescription(task.getDescription());
        taskFullDto.setTitle(task.getTitle());
        taskFullDto.setProjectId(task.getProject().getId());
        taskFullDto.setUserId(task.getUser().getId());
        taskFullDto.setDate(task.getDate());
        taskFullDto.setTaskStatus(task.getTaskStatus());
        taskFullDto.setComments(task.getComments().stream()
                .map(CommentMapper::commentToCommentFullDto)
                .collect(Collectors.toList()));
        return taskFullDto;
    }

    public static Task taskPostDtoToTask(TaskCreateDto taskCreateDto) {
        Task task = new Task();
        task.setTitle(taskCreateDto.getTitle());
        task.setDescription(taskCreateDto.getDescription());
        task.setDate(taskCreateDto.getDate());
        task.setTaskStatus(CREATED);
        User user = new User();
        user.setId(taskCreateDto.getUserId());
        task.setUser(user);
        Project project = new Project();
        project.setId(taskCreateDto.getProjectId());
        task.setProject(project);
        return task;
    }

    public static void taskPostDtoMergeWithTask(TaskUpdateDto taskUpdateDto, Task task) {
        if (taskUpdateDto.getTitle() != null) {
            task.setTitle(taskUpdateDto.getTitle());
        }
        if (taskUpdateDto.getDescription() != null) {
            task.setDescription(taskUpdateDto.getDescription());
        }
        if (taskUpdateDto.getDate() != null) {
            task.setDate(taskUpdateDto.getDate());
        }
        if (taskUpdateDto.getUserId() != null) {
            User user = new User();
            user.setId(taskUpdateDto.getUserId());
            task.setUser(user);
        }
        if (taskUpdateDto.getProjectId() != null) {
            Project project = new Project();
            project.setId(taskUpdateDto.getProjectId());
            task.setProject(project);
        }
    }
}
