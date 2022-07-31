package ru.homework.tasktracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.homework.tasktracker.model.dto.UserCreateDto;
import ru.homework.tasktracker.model.dto.UserFullDto;
import ru.homework.tasktracker.model.dto.UserUpdateDto;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.model.filter.UserFilter;

import java.util.List;

public interface UserService {
    UserFullDto findById(Long id);

    UserFullDto findByEmail(String email);

    Page<UserFullDto> findAll(UserFilter userFilter, Pageable pageable);

    void addProject(Long userId, Long projectId);

    Long save(UserCreateDto user);

    void delete(Long id);

    void update(UserUpdateDto user, Long id);

    UserFullDto findUserWithMaxNumberTasks(UserFilter userFilter);
}
