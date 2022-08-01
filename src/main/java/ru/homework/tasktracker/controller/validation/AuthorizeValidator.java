package ru.homework.tasktracker.controller.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.dto.TaskFullDto;
import ru.homework.tasktracker.security.UserDetailsImpl;
import ru.homework.tasktracker.service.TaskService;

@Component
@RequiredArgsConstructor
public class AuthorizeValidator {
    private final TaskService taskService;

    public boolean thisTaskBelongToUser(Long id) {
        TaskFullDto task = taskService.findById(id);
        Long userId = task.getUserId();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        return user.getUser().getId().equals(userId);
    }
}
