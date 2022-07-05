package ru.homework.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.repository.UserRepository;
import ru.homework.tasktracker.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Пользователя с id %s не существует", id)));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
        log.info("Пользователь {} создан", user.getName());
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
        log.info("Пользователь {} обновлен", user.getName());
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
        log.info("Пользователь {} удален", user.getName());
    }
}
