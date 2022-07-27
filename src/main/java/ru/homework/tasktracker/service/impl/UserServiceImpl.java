package ru.homework.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homework.tasktracker.model.entity.User;
import ru.homework.tasktracker.model.filter.UserFilter;
import ru.homework.tasktracker.repository.UserRepository;
import ru.homework.tasktracker.service.UserService;

import java.util.List;

import static java.util.Comparator.comparingInt;
import static ru.homework.tasktracker.specification.UserSpecification.generateSpecificationByUserFilter;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Пользователя с id %s не существует", id)));
    }

    @Override
    public List<User> findAll(UserFilter userFilter) {
        return userRepository.findAll(generateSpecificationByUserFilter(userFilter));
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        this.findById(user.getId());
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = this.findById(id);
        userRepository.delete(user);
    }

    @Override
    public User findUserWithMaxNumberTasks(UserFilter userFilter) {
        List<User> users = userRepository.findAll(generateSpecificationByUserFilter(userFilter));
        return users.stream()
                .max(comparingInt(u -> u.getTasks().size()))
                .orElseThrow(() -> new RuntimeException("С такими значениями фильтров никого не нашлось"));
    }

}
