package ru.homework.tasktracker.mapper;

import ru.homework.tasktracker.model.dto.TaskFullDto;
import ru.homework.tasktracker.model.dto.TaskPostDto;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.entity.User;

import java.util.stream.Collectors;

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

    public static Task taskPostDtoToTask(TaskPostDto taskPostDto) {
        Task task = new Task();
        task.setTitle(taskPostDto.getTitle());
        task.setDescription(taskPostDto.getDescription());
        task.setDate(taskPostDto.getDate());
        User user = new User();
        user.setId(taskPostDto.getUserId());
        task.setUser(user);
        Project project = new Project();
        project.setId(taskPostDto.getProjectId());
        task.setProject(project);
        return task;
    }

    public static void taskPostDtoMergeWithTask(TaskPostDto taskPostDto, Task task) {
        if (taskPostDto.getTitle() != null) {
            task.setTitle(taskPostDto.getTitle());
        }
        if (taskPostDto.getDescription() != null) {
            task.setDescription(taskPostDto.getDescription());
        }
        if (taskPostDto.getDate() != null) {
            task.setDate(taskPostDto.getDate());
        }
        if (taskPostDto.getUserId() != null) {
            User user = new User();
            user.setId(taskPostDto.getUserId());
            task.setUser(user);
        }
        if (taskPostDto.getProjectId() != null) {
            Project project = new Project();
            project.setId(taskPostDto.getProjectId());
            task.setProject(project);
        }
    }
}
