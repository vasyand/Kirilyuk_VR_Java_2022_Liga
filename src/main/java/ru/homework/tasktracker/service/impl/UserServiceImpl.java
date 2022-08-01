package ru.homework.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.homework.tasktracker.exception.EntityAlreadyExists;
import ru.homework.tasktracker.exception.EntityNotFoundException;
import ru.homework.tasktracker.mapper.UserMapper;
import ru.homework.tasktracker.model.dto.UserCreateDto;
import ru.homework.tasktracker.model.dto.UserFullDto;
import ru.homework.tasktracker.model.dto.UserUpdateDto;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.model.filter.UserFilter;
import ru.homework.tasktracker.repository.UserRepository;
import ru.homework.tasktracker.service.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.*;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.groupingBy;
import static ru.homework.tasktracker.mapper.UserMapper.*;
import static ru.homework.tasktracker.specification.UserSpecification.generateSpecificationByUserFilter;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserFullDto findById(Long id) {
        User user = this.findUserById(id);
        return userToUserFullDto(user);
    }

    @Override
    @Transactional
    public UserFullDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(format("Пользователя с email %s не существут", email)));
        return userToUserFullDto(user);
    }

    @Override
    @Transactional
    public Page<UserFullDto> findAll(UserFilter userFilter, Pageable pageable) {
        Page<User> users;
        if (userFilter != null) {
            users = userRepository.findAll(generateSpecificationByUserFilter(userFilter), pageable);
        } else {
            users = userRepository.findAll(pageable);
        }
        return users.map(UserMapper::userToUserFullDto);
    }

    @Override
    @Transactional
    public Long save(UserCreateDto userCreateDto) {
        if (this.emailIsNotExist(userCreateDto.getEmail())) {
            User user = userCreateDtoToUser(userCreateDto);
            user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
            return userRepository.save(user).getId();
        } else {
            throw new EntityAlreadyExists(format("Пользователь с email %s уже существует!",
                    userCreateDto.getEmail()));
        }
    }

    @Override
    @Transactional
    public void update(UserUpdateDto userUpdateDto, Long id) {
        User user = this.findUserById(id);
        userUpdateDtoMergeWithUser(userUpdateDto, user);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = this.findUserById(id);
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public UserFullDto findUserWithMaxNumberTasks(UserFilter userFilter) {
        List<User> users = userRepository.findAll(generateSpecificationByUserFilter(userFilter));
        Map<UserFullDto, Long> countUser = users.stream()
                .map(UserMapper::userToUserFullDto)
                .collect(groupingBy(u -> u, Collectors.counting()));
        return countUser.entrySet().stream()
                .max(comparingLong(Map.Entry::getValue))
                .get()
                .getKey();
    }

    @Override
    @Transactional
    public void addProject(Long userId, Long projectId) {
        User user = findUserById(userId);
        Project project = new Project();
        project.setId(projectId);
        user.getProjects().add(project);
        project.getUsers().add(user);
        userRepository.save(user);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Пользователя с id %s не существует", id)));
    }

    private boolean emailIsNotExist(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }
}
